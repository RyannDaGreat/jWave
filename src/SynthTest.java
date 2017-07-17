import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
@SuppressWarnings({"WeakerAccess","Duplicates"})
public class SynthTest
{
    public static void main(String[] args) throws LineUnavailableException, InterruptedException, IOException
    {
        WaveCube modulin=WaveCube.modulinOscillator();
        modulin.xModInput=new Constant(.3);
        Triangle triangle=new Triangle();
        triangle.inputFrequency=new Constant(.1);
        LinearFunction majorScalePitchArpeggio=LinearFunction.majorNoteToPitch(LinearFunction.round(new LinearFunction(triangle,x->x*9-8)));
        Triangle pitchVibrato=new Triangle();
        pitchVibrato.inputFrequency=new Constant(7);
        pitchVibrato.inputAmplitude=new Constant(.05);
        modulin.inputFrequency=LinearFunction.pitchToFrequency(BilinearFunction.add(new Portamento(majorScalePitchArpeggio,new Constant(.00000000000000000000001)),pitchVibrato));
        Phasor phasor=new Phasor(modulin,new GetTime(),new Constant(-.2));
        Portamento lowPass=new Portamento(phasor,new Constant(1e-30));
        TrilinearFunction blend=TrilinearFunction.blend(phasor,lowPass,new Constant(.8));
        // Echo echo=new Echo(blend,.051d);
        // Echo echo2=new Echo(echo,3*.051d);

        Triangle ud=new Triangle();
        ud.inputFrequency=new Constant(.1);
        BlendyEcho echo2=new BlendyEcho(blend,new LinearFunction(ud,x->(x+1)*.011),new Constant(.91));
        // echo2.falloffFactor=new Constant(.9);
        // LinearFunction amplify=new LinearFunction(echo,x->.2*x);

        // SynthEngine.setOutputModule(TrilinearFunction.blend(echo,echo2,new Constant(.5)));
        SynthEngine.setOutputModule(echo2);
    }
}