package Synth;
import Common.r;
import Synth.LinearModules.Constant;
import Synth.LinearModules.LinearModule;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
//Features: 16 bit OR 8-bit mono sound (you can easily switch between the two!), Multithreaded buffering (so less clicks and pops if too much lag) and maintaining tempo (regardless of lag), custom samplerate, modular input, automatic buffer size minimization calculation given any samplerate (so the user controls from the Arduino can change notes really really fast etc)
//POSSIBLY: Remove crossfade because it seems to be useless
public class SynthEngine//I make the sound on the speakers. roar :}
{
    public static LinearModule audioModule=new Constant(0);
    public static LinearModule crossFadeProportion=new Constant(1);//<-- Can't tell the difference whether it's 0 or 1. Should be between 0 and 1; anything outside of that range will be clamped. A bit low level; this determines how much of the bufferBytes should be cross-faded into a newly created bufferBytes after repeating when it lags (to avoid hearing a popping sound).
    public static String outputFilePath="/Users/Ryan/Desktop/test.wav";//Set to null if you don't want to save any files. The file will be saved as you exit the java program via a shutdown hook.
    public static AudioFileFormat.Type outputFileType=AudioFileFormat.Type.WAVE;
    public static boolean mustMaintainTempo=true;//UserInput++ false ⟹ smoother sound but incorrect tempo. If set to true, it means if the buffers lag it will keep its tempo anyway, even though it means the buffers will be out of sync (and so it will sound noisy)
    public static final int SAMPLE_RATE=44100;
    private static final int ↈbitsPerSample=8;//UserInput++ LIMITED CHOICES: You can only have either 8-bit or 16-bit audio. ↈbitsPerSample ∈ {8，16}  (8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.)
    public static long getCurrentↈSamples()
    {
        return currentSampleNumber;
    }
    public static double getCurrentTime()
    {
        return (double)getCurrentↈSamples()/SAMPLE_RATE;
    }
    private static long currentSampleNumber;
    private static final int bufferↈSamples=SAMPLE_RATE/43+1;//⟵Magic # i stumbled on by trial/error and 44100/1024≈43.06  // 44100⟶1024 and 441000⟶10240. Determines latency! Lower -> Faster response but glitchier audio
    private static final int bufferↈBytes=bufferↈSamples*ↈbitsPerSample/8;
    private static SourceDataLine line;
    private static boolean bufferMakerIsBusy;
    private static SynthBuffer newBuffer;
    private static SynthBuffer buffer;
    private static SynthBuffer oldBuffer;
    private static final ByteArrayOutputStream byteHistory=new ByteArrayOutputStream();//Used for writing files
    private static final AudioFormat audioFormat=new AudioFormat(SAMPLE_RATE,ↈbitsPerSample,1,true,true);
    @SuppressWarnings("ConstantConditions")
    private static byte[] doublesToAudioBytes(double[] doubles)
    {
        switch(ↈbitsPerSample)
        {
            case 8:
                return r.doublesTo8BitAudioBytes(doubles);//8-bit audio is also available: bytes=r.doublesTo8BitAudioBytes(doubles);
            case 16:
                return r.doublesTo16BitAudioBytes(doubles);//8-bit audio is also available: bytes=r.doublesTo8BitAudioBytes(doubles);
            case 32:
                return r.doublesTo32BitAudioBytes(doubles);//8-bit audio is also available: bytes=r.doublesTo8BitAudioBytes(doubles);
        }
        assert false:"Only 8-bit and 16-bit audio are supported by SynthEngine, but ↈbitsPerSample == "+ↈbitsPerSample;
        return null;
    }
    @SuppressWarnings("unused")//For logical completeness I'm keeping those in there.
    private final static class SynthBuffer
    {
        private final double[] doubles;//Immutable: You should not change the values in these arrays (even though you can because this is an inner class!). That could get messy.
        private final byte[] bytes;//↑↑↑ Ditto for this one ↑↑↑
        SynthBuffer()
        {
            doubles=new double[bufferↈSamples];
            for(int i=0;i<bufferↈSamples;i++)//Get audio samples from SynthEngine's audioModule and load them into this buffer!
            {
                doubles[i]=audioModule.getSample();
                currentSampleNumber++;//Note that this method is responsible for driving the clock in the modules, AKA currentSampleNumber
            }
            bytes=doublesToAudioBytes(doubles);
        }
        SynthBuffer(double[] doubles)//This constructor will be used for buffer cross-fading
        {
            this.doubles=doubles;
            bytes=doublesToAudioBytes(doubles);
        }
        final void write()//Play this buffer on the audio output. NOTE: This will block the thread that called this until the audio finishes playing.
        {
            line.write(bytes,0,bufferↈBytes);
            //noinspection ConstantConditions
            if(outputFilePath!=null)//If we're planning on saving a file
            {
                try
                {
                    byteHistory.write(bytes);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                    assert false;
                }
            }
        }
    }
    static
    {
        try
        {
            line=AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat,bufferↈSamples);
            line.start();
            new Thread(()->//Run the sound-making part of the synth on a new thread so we can control the inputs without having to worry about generating sound
                       {
                           Thread bufferMaker=new Thread(new Runnable()
                           {
                               synchronized public void run()
                               {
                                   //noinspection InfiniteLoopStatement
                                   while(true)
                                   {
                                       bufferMakerIsBusy=true;
                                       if(newBuffer==null)
                                       {
                                           newBuffer=new SynthBuffer();
                                       }
                                       bufferMakerIsBusy=false;
                                       try
                                       {
                                           this.wait();
                                       }
                                       catch(InterruptedException ignored)
                                       {
                                       }
                                   }
                               }
                           });
                           newBuffer=new SynthBuffer();
                           bufferMaker.start();
                           //noinspection SynchronizationOnLocalVariableOrMethodParameter
                           synchronized(bufferMaker)
                           {
                               //noinspection InfiniteLoopStatement
                               while(true)
                               {
                                   if(newBuffer!=null)//Fresh new bufferBytes
                                   {
                                       buffer=newBuffer;
                                       newBuffer=null;
                                       if(!bufferMakerIsBusy)
                                       {
                                           bufferMaker.interrupt();
                                       }
                                       if(oldBuffer!=null)//We create a cross-fade buffer between oldBuffer and buffer, play that, then set oldBuffer to null
                                       {
                                           final int crossFadeSamples=(int)r.clamp(crossFadeProportion.getSample(),0,1)*bufferↈSamples;
                                           for(int i=0;i<crossFadeSamples;i++)//Cross-fade the oldBufferBytes into the bufferBytes to make it less crackle-poppy
                                           {
                                               double α=(double)i/crossFadeSamples;
                                               oldBuffer.doubles[i]=r.blend(oldBuffer.doubles[i],buffer.doubles[i],α);
                                           }
                                           new SynthBuffer(oldBuffer.doubles).write();
                                           oldBuffer=null;
                                       }
                                       else
                                       {
                                           buffer.write();
                                       }
                                   }
                                   else //We're lagging a bit - reuse the old bufferBytes
                                   {
                                       if(mustMaintainTempo)
                                       {
                                           currentSampleNumber+=bufferↈSamples;
                                           oldBuffer=buffer;
                                           if(!bufferMakerIsBusy)
                                           {
                                               bufferMaker.interrupt();
                                           }
                                           buffer.write();
                                       }
                                   }
                               }
                           }
                       }).start();
            //noinspection ConstantConditions
            if(outputFilePath!=null)//The user wishes to save some file
            {
                Runtime.getRuntime().addShutdownHook(new Thread(SynthEngine::saveAudioFile));
            }
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    private static void saveAudioFile()
    {
        System.out.println("Saving audio to file: "+outputFilePath);
        byte[] bytes=byteHistory.toByteArray();
        try
        {
            AudioSystem.write(new AudioInputStream(new ByteArrayInputStream(bytes),audioFormat,bytes.length),outputFileType,new File(outputFilePath));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
// package Synth;
// import Common.r;
// import Synth.LinearModules.Constant;
// import Synth.LinearModules.LinearModule;
// //Features: 16 bit mono sound, Multithreaded buffering (so less clicks and pops if too much lag) and maintaining tempo (regardless of lag), custom samplerate, modular input
// import javax.sound.sampled.AudioFormat;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.LineUnavailableException;
// import javax.sound.sampled.SourceDataLine;
// public class SynthEngine//I make the sound on the speakers. roar :}
// {
//     public static final int SAMPLE_RATE=441000;
//     public static boolean mustMaintainTempo=true;//UserInput++ false ⟹ smoother sound but incorrect tempo. If set to true, it means if the buffers lag it will keep its tempo anyway, even though it means the buffers will be out of sync (and so it will sound noisy)
//     private static long currentSampleNumber=0;
//     public static long getCurrentↈSamples()
//     {
//         return currentSampleNumber;
//     }
//     public static double getCurrentTime()
//     {
//         return (double)getCurrentↈSamples()/SAMPLE_RATE;
//     }
//     public static void setAudioModule(LinearModule module)
//     {
//         audioModule=module;
//     }
//     private static final int bufferↈSamples=SAMPLE_RATE/43+1;//⟵Magic # i stumbled on by trial/error and 44100/1024≈43.06  // 44100⟶1024 and 441000⟶10240. Determines latency! Lower -> Faster response but glitchier audio
//     private static final int ↈbitsPerSample=2*8;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
//     private static SourceDataLine line;
//     private static LinearModule audioModule=new Constant(0);
//     private static byte[] newBufferBytes;
//     private static byte[] bufferBytes;
//     static
//     {
//         try
//         {
//             final AudioFormat audioFormat=new AudioFormat(SAMPLE_RATE,ↈbitsPerSample,1,true,true);
//             line=AudioSystem.getSourceDataLine(audioFormat);
//             line.open(audioFormat,bufferↈSamples);
//             line.start();
//             new Thread(()->//Run the sound-making part of the synth on a new thread so we can control the inputs without having to worry about generating sound
//                        {
//                            Thread bufferMaker=new Thread(new Runnable()
//                            {
//                                synchronized public void run()
//                                {
//                                    //noinspection InfiniteLoopStatement
//                                    while(true)
//                                    {
//                                        if(newBufferBytes==null)
//                                        {
//                                            newBufferBytes=get16BitBuffer();
//                                        }
//                                        try
//                                        {
//                                            this.wait();
//                                        }
//                                        catch(InterruptedException ignored)
//                                        {
//                                        }
//                                    }
//                                }
//                            });
//                            bufferMaker.start();
//                            bufferBytes=get16BitBuffer();
//                            //noinspection SynchronizationOnLocalVariableOrMethodParameter
//                            synchronized(bufferMaker)
//                            {
//                                //noinspection InfiniteLoopStatement
//                                while(true)
//                                {
//                                    if(newBufferBytes!=null)//Fresh new bufferBytes
//                                    {
//                                        bufferBytes=newBufferBytes;
//                                        newBufferBytes=null;
//                                    }
//                                    else if(mustMaintainTempo)//We're lagging a bit - reuse the old bufferBytes
//                                    {
//                                        currentSampleNumber+=bufferↈSamples;
//                                    }
//                                    bufferMaker.interrupt();
//                                    line.write(bufferBytes,0,bufferↈSamples*ↈbitsPerSample/8);
//                                }
//                            }
//                        }).start();
//         }
//         catch(LineUnavailableException e)
//         {
//             e.printStackTrace();
//         }
//     }
//     private static double[] getDoubleBuffer()
//     {
//         double[] bufferBytes=new double[bufferↈSamples];
//         for(int i=0;i<bufferBytes.length;i++)
//         {
//             bufferBytes[i]=audioModule.getSample();
//             currentSampleNumber++;
//         }
//         return bufferBytes;
//     }
//     private static byte[] get16BitBuffer()
//     {
//         return r.doublesTo16BitAudioBytes(getDoubleBuffer());
//     }
//     @SuppressWarnings("unused")
//     private static byte[] get8BitBuffer()
//     {
//         return r.doublesTo8BitAudioBytes(getDoubleBuffer());
//     }
// }
