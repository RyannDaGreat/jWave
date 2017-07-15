import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
@SuppressWarnings({"WeakerAccess","Duplicates"})
public class SynthTest
{
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException
    {
        WaveCube wc=WaveCube.modulinOscillator();
        WaveCube wc2=WaveCube.modulinOscillator();
        Square s=new Square();
        s.inputFrequency=new Constant(1);
        wc.inputFrequency=new Constant(100);
        LinearFunction gate=new LinearFunction(s,x->x/2+.5);
        wc2.inputFrequency=new Frequency(441);
        BilinearFunction m=BilinearFunction.multiply(wc,gate);
        Reverb r=Reverb.effector(m);
        SynthEngine.setOutputModule(r);
        // Sinusoid sin=new Sinusoid();
        // wc.inputFrequency=new Constant(110);
        // sin.inputFrequency=new LinearFunction(LinearFunction.pitchToFrequency(new GetTime()),x->x*.1);
        // wc.xModInput=new LinearFunction(sin,x->x*x/2.4);//LinearFunction.pitchToFrequency(BilinearFunction.multiply(sin,BilinearFunction.add(sin,new Constant(-10))));
        // SynthEngine.setOutputModule(wc);
    }
}