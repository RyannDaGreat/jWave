import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
@SuppressWarnings("WeakerAccess")
public class SynthTest
{
    protected static final int SAMPLE_RATE=44100;//16*1024;
    static final int bufferSize=1024;//Determines latency! Lower -> Faster response but glitchier audio
    static final int bitsPerSample=16;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
    static final AudioFormat af=new AudioFormat(SAMPLE_RATE,bitsPerSample,1,true,true);
    static SourceDataLine line;
    static
    {
        try
        {
            line=AudioSystem.getSourceDataLine(af);
            line.open(af,bufferSize);
            line.start();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws LineUnavailableException, InterruptedException
    {
        // Sawtooth saw=new Sawtooth();
        WaveCubeReader saw=new WaveCubeReader();
        saw.setX(.3);
        // Legato filteredSaw=new Legato(saw);
        // filteredSaw.setAlphaPerSecond(0.025);
        saw.setPitch(-24);
        // for(double pitch=-10;pitch<=100;pitch++)
        r.tic();
        while(true)
        {
            saw.setPitch((Math.cos(r.toc()*.25)+1)*35-24);
            // System.out.println(saw.x);
            int numberOfSamples=bufferSize/bitsPerSample*8;
            // r.delay(1/SAMPLE_RATE);
           line.write(saw.get16BitBuffer(numberOfSamples,SAMPLE_RATE),0,numberOfSamples*bitsPerSample/8);//numberOfSamples*2 because 16 bit buffer
            // line.write(saw.get16BitBuffer(numberOfSamples,SAMPLE_RATE),0,numberOfSamples*2);//numberOfSamples*2 because 16 bit buffer
        }
        // line.drain();
        // line.close();
    }
}