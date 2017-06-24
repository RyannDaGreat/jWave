import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

@SuppressWarnings("WeakerAccess")
public class SynthTest2
{
    protected static final int SAMPLE_RATE=44100;//16*1024;
    static final int bitsPerSample=16;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
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
        while(true)
        {
            Sawtooth saw=new Sawtooth();
            for(double pitch=-24;pitch<=24;pitch++)
            {
                saw.setPitch(pitch);
                int count=line.write(saw.get16BitBuffer(SAMPLE_RATE/2,SAMPLE_RATE),0,SAMPLE_RATE/2);
                System.out.println(count);
            }
            r.scan("Enter");
        }
        // line.drain();
        // line.close();
    }
}