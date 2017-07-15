import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
@SuppressWarnings("WeakerAccess")
public class SynthTest
{
    protected static final int SAMPLE_RATE=10000;//16*1024;
    static final int bufferSize=SAMPLE_RATE/43+1;//⟵Magic i stumbled on by trial/error and 44100/1024≈43.06  // 44100⟶1024 and 441000⟶10240. Determines latency! Lower -> Faster response but glitchier audio
    static final int bitsPerSample=2*8;//8⟷Byte，16⟷Short，32⟷Int. It appears that, for some reason, trying to use 32 bit causes some audio error. I don't know why.
    static SourceDataLine line;
    static
    {
        try
        {
            final AudioFormat af=new AudioFormat(SAMPLE_RATE,bitsPerSample,1,true,true);
            line=AudioSystem.getSourceDataLine(af);
            line.open(af,bufferSize);
            line.start();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException
    {
        // Sawtooth saw=new Sawtooth();
        WaveCubeReader saw=new WaveCubeReader();
        saw.setX(.3);
        Legato filteredSaw=new Legato(saw);
        Reverb reverbedSaw=new Reverb(saw,WaveCube.loadWaveTable("/Users/Ryan/Desktop/RyanCourseSiteGeneratorThirdRecovery/Digilin/FromEffector/EffectorReverb.png"),44100);
        filteredSaw.setAlphaPerSecond(0.025);
        saw.setPitch(-24);
        // for(double pitch=-10;pitch<=100;pitch++)
        r.tic();
        while(true)
        {

            saw.setPitch(Math.sin(r.toc()*.25)*5+Math.sin(r.toc()*.3)*Math.sin(r.toc()*.3)*(Math.acos(Math.cos(r.toc()*50)))*.5);
            // System.out.println(saw.x);
            int numberOfSamples=bufferSize/bitsPerSample*8;
            // r.delay(1/SAMPLE_RATE);
           line.write(reverbedSaw.get16BitBuffer(numberOfSamples,SAMPLE_RATE),0,numberOfSamples*bitsPerSample/8);//numberOfSamples*2 because 16 bit buffer
            // line.write(saw.get16BitBuffer(numberOfSamples,SAMPLE_RATE),0,numberOfSamples*2);//numberOfSamples*2 because 16 bit buffer
        }
        // line.drain();
        // line.close();
    }
}