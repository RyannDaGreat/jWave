package Synth;
import Common.r;
import Synth.LinearModules.Constant;
import Synth.LinearModules.LinearModule;
//Features: 16 bit mono sound, Multithreaded buffering (so less clicks and pops if too much lag) and maintaining tempo (regardless of lag), custom samplerate, modular input
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
public class SynthEngine//I make the sound on the speakers. roar :}
{
    public static final int SAMPLE_RATE=441000;
    public static boolean mustMaintainTempo=true;//UserInput++ false ⟹ smoother sound but incorrect tempo. If set to true, it means if the buffers lag it will keep its tempo anyway, even though it means the buffers will be out of sync (and so it will sound noisy)
    private static long currentSampleNumber=0;
    public static long getCurrentↈSamples()
    {
        return currentSampleNumber;
    }
    public static double getCurrentTime()
    {
        return (double)getCurrentↈSamples()/SAMPLE_RATE;
    }
    public static LinearModule audioModule=new Constant(0);
    public static LinearModule crossFadeProportion=new Constant(1);//Should be between 0 and 1; anything outside of that range will be clamped. A bit low level; this determines how much of the bufferBytes should be cross-faded into a newly created bufferBytes after repeating when it lags (to avoid hearing a popping sound).
    private static final int bufferↈSamples=SAMPLE_RATE/43+1;//⟵Magic # i stumbled on by trial/error and 44100/1024≈43.06  // 44100⟶1024 and 441000⟶10240. Determines latency! Lower -> Faster response but glitchier audio
    private static final int ↈbitsPerSample=2*8;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
    private static final int bufferↈBytes=bufferↈSamples*ↈbitsPerSample/8;
    private static SourceDataLine line;
    private static byte[] newBufferBytes;
    private static byte[] oldBufferBytes;
    private static byte[] bufferBytes;
    private static boolean bufferMakerIsBusy;
    static
    {
        try
        {
            final AudioFormat af=new AudioFormat(SAMPLE_RATE,ↈbitsPerSample,1,true,true);
            line=AudioSystem.getSourceDataLine(af);
            line.open(af,bufferↈSamples);
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
                                       if(newBufferBytes==null)
                                       {
                                           newBufferBytes=get16BitBuffer();
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
                           bufferMaker.start();
                           bufferBytes=get16BitBuffer();
                           //noinspection SynchronizationOnLocalVariableOrMethodParameter
                           synchronized(bufferMaker)
                           {
                               //noinspection InfiniteLoopStatement
                               while(true)
                               {
                                   if(newBufferBytes!=null)//Fresh new bufferBytes
                                   {
                                       bufferBytes=newBufferBytes;
                                       newBufferBytes=null;
                                       if(!bufferMakerIsBusy)
                                       {
                                           bufferMaker.interrupt();
                                       }
                                       if(oldBufferBytes!=null)
                                       {
                                           final int crossFadeSamples=(int)r.clamp(crossFadeProportion.getSample(),0,1)*bufferↈBytes;
                                           for(int i=0;i<crossFadeSamples;i++)//Crossfade the oldBufferBytes into the bufferBytes to make it less crackle-poppy
                                           {
                                               bufferBytes[i]=(byte)(((int)bufferBytes[i]*i+(int)oldBufferBytes[i]*(crossFadeSamples-i))/crossFadeSamples);
                                           }
                                           line.write(oldBufferBytes,0,bufferↈBytes);
                                           oldBufferBytes=null;
                                       }
                                       else
                                       {
                                           line.write(bufferBytes,0,bufferↈBytes);
                                       }
                                   }
                                   else //We're lagging a bit - reuse the old bufferBytes
                                   {
                                       if(mustMaintainTempo)
                                       {
                                           currentSampleNumber+=bufferↈSamples;
                                           oldBufferBytes=bufferBytes;
                                           if(!bufferMakerIsBusy)
                                           {
                                               bufferMaker.interrupt();
                                           }
                                           line.write(bufferBytes,0,bufferↈBytes);
                                       }
                                   }
                               }
                           }
                       }).start();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    private static double[] getDoubleBuffer()
    {
        double[] buffer=new double[bufferↈSamples];
        for(int i=0;i<buffer.length;i++)
        {
            buffer[i]=audioModule.getSample();
            currentSampleNumber++;
        }
        return buffer;
    }
    private static byte[] get16BitBuffer()
    {
        return r.doublesTo16BitAudioBytes(getDoubleBuffer());
    }
    @SuppressWarnings("unused")
    private static byte[] get8BitBuffer()
    {
        return r.doublesTo8BitAudioBytes(getDoubleBuffer());
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
//             final AudioFormat af=new AudioFormat(SAMPLE_RATE,ↈbitsPerSample,1,true,true);
//             line=AudioSystem.getSourceDataLine(af);
//             line.open(af,bufferↈSamples);
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
