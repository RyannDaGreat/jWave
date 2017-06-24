import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

@SuppressWarnings("WeakerAccess")
public class SynthTest2
{
    protected static final int SAMPLE_RATE=44100;//16*1024;
    public static byte[] createSinWaveBuffer(double freq,int ms)
    {
        int samples=ms*SAMPLE_RATE/1000;
        double[] output=new double[samples];
        //
        double period=SAMPLE_RATE/freq;
        for(int i=0;i<output.length;i++)
        {
            double angle=2.0*Math.PI*i/period;
            output[i]=Math.sin(angle);
        }
        return rOutpost.doublesTo32BitAudioBytes(output);
    }
    static final int bitsPerSample=16;//8⟷Byte，16⟷Short，32⟷Int
    static final AudioFormat af=new AudioFormat(SAMPLE_RATE,bitsPerSample,1,true,true);
    static SourceDataLine line;
    static
    {
        try
        {
            line=AudioSystem.getSourceDataLine(af);
            line.open(af,SAMPLE_RATE);
            line.start();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws LineUnavailableException
    {
        for(double freq=400;freq<=800;)
        {
            byte[] toneBuffer=createSinWaveBuffer(freq,50);
            int count=line.write(toneBuffer,0,toneBuffer.length);
            System.out.println(count);
            freq+=20;
        }
        line.drain();
        line.close();
    }
}