import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
public class SynthEngine//I make the sound on the speakers. roar :}
{
    public static final int SAMPLE_RATE=44100;
    private static long currentSampleNumber=0;
    public static long getCurrentↈSamples()
    {
        return currentSampleNumber;
    }
    public static double getCurrentTime()
    {
        return (double)getCurrentↈSamples()/SAMPLE_RATE;
    }
    public static void setOutputModule(LinearModule module)
    {
        inputModule=module;
    }
    //
    private static final int bufferSize=SAMPLE_RATE/43+1;//⟵Magic i stumbled on by trial/error and 44100/1024≈43.06  // 44100⟶1024 and 441000⟶10240. Determines latency! Lower -> Faster response but glitchier audio
    private static final int bitsPerSample=2*8;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
    private static SourceDataLine line;
    private static LinearModule inputModule=new Constant(0);
    static
    {
        try
        {
            final AudioFormat af=new AudioFormat(SAMPLE_RATE,bitsPerSample,1,true,true);
            line=AudioSystem.getSourceDataLine(af);
            line.open(af,bufferSize);
            line.start();
            new Thread(()->//Run the sound-making part of the synth on a new thread so we can control the inputs without having to worry about generating sound
                       {
                           //noinspection InfiniteLoopStatement
                           while(true)
                           {
                               line.write(get16BitBuffer(),0,bufferSize*bitsPerSample/8);
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
        double[] buffer=new double[bufferSize];
        for(int i=0;i<buffer.length;i++)
        {
            buffer[i]=inputModule.getSample();
            currentSampleNumber++;
        }
        return buffer;
    }
    private static byte[] get16BitBuffer()
    {
        return rOutpost.doublesTo16BitAudioBytes(getDoubleBuffer());
    }
    private static byte[] get8BitBuffer()
    {
        return rOutpost.doublesTo8BitAudioBytes(getDoubleBuffer());
    }
}
