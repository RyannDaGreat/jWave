import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
@SuppressWarnings("WeakerAccess")
public class SynthTest
{
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException
    {
        Sawtooth saw=new Sawtooth();
        // WaveCubeReader saw=new WaveCubeReader();
        // saw.setX(.3);
        // Legato filteredSaw=new Legato(saw);
        // Reverb reverbedSaw=new Reverb(saw,WaveCube.loadWaveTable("/Users/Ryan/Desktop/RyanCourseSiteGeneratorThirdRecovery/Digilin/FromEffector/EffectorReverb.png"),44100);
        // filteredSaw.setAlphaPerSecond(0.025);
        // saw.setPitch(-24);
        // for(double pitch=-10;pitch<=100;pitch++)
        Frequency sawFrequency=new Frequency();
        saw.inputFrequency=sawFrequency;
        SynthEngine.setOutputModule(saw);
        while(true)
        {
            sawFrequency.setPitch(Math.sin(r.toc()*.25)*5+Math.sin(r.toc()*.3)*Math.sin(r.toc()*.3)*(Math.acos(Math.cos(r.toc()*50)))*.5);
        }
        // line.drain();
        // line.close();
    }
}