package Synth;
import Synth.LinearModules.*;

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
        pitchVibrato.inputFrequency=new Constant(10);
        pitchVibrato.inputAmplitude=new Constant(.4);
        modulin.inputFrequency=LinearFunction.pitchToFrequency(BilinearFunction.add(new Portamento(majorScalePitchArpeggio,new Constant(.00000000000000000000001)),pitchVibrato));
        Phasor phasor=new Phasor(modulin,new GetTime(),new Constant(-.2));
        Portamento lowPass=new Portamento(phasor,new Constant(1e-30));
        // LinearModule blend=new Square();//TrilinearFunction.blend(phasor,lowPass,new Constant(.8));
        // ((Square)blend).inputFrequency=new Constant(.2);
        LinearModule blend=TrilinearFunction.blend(phasor,lowPass,new Constant(.8));
        // Echo echo=new Echo(blend,.051d);
        // Echo echo2=new Echo(echo,3*.051d);
        double t=.151;
        double alpha=.25;
        HardcodedConvolutionalReverb q=new HardcodedConvolutionalReverb(blend);
        Square square=new Square();
        square.inputFrequency=new Constant(0);
        LinearFunction gate=new LinearFunction(square,(x)->(0*x+1)/2);
        LinearModule preEcho=BilinearFunction.multiply(modulin,gate);//new BlendyEcho(q,.01,new Constant(.2));
        LinearModule echo1 =new BlendyEcho(preEcho,t/1+.04,new Constant(alpha/1));//a
        LinearModule echo2 =new BlendyEcho(preEcho,t/2+.04,new Constant(alpha/2));//b+a
        LinearModule echo3 =new BlendyEcho(preEcho,t/3+.04,new Constant(alpha/3));//c+a
        LinearModule echo4 =new BlendyEcho(preEcho,t/4+.04,new Constant(alpha/4));//b+d+a
        LinearModule echo5 =new BlendyEcho(preEcho,t/5+.04,new Constant(alpha/5));//e+a
        LinearModule echo6 =new BlendyEcho(preEcho,t/6+.04,new Constant(alpha/6));//a+c+b+f
        LinearModule echo7 =new BlendyEcho(preEcho,t/7+.04,new Constant(alpha/7));//a+g
        LinearModule echo8 =new BlendyEcho(preEcho,t/8+.04,new Constant(alpha/8));//a+g
        LinearModule echo9 =new BlendyEcho(preEcho,t/9+.04,new Constant(alpha/9));//a+g
        LinearModule echo10=new BlendyEcho(preEcho,t/10+.04,new Constant(alpha/10));//a+g
        LinearModule echo11=new BlendyEcho(preEcho,t/11+.04,new Constant(alpha/11));//a+g
        LinearModule echo12=new BlendyEcho(preEcho,t/12+.04,new Constant(alpha/12));//a+g
        PolylinearFunction echo=new PolylinearFunction(x->x[0]+x[1]+x[2]+x[3]+x[4]+x[5]+x[6]+x[7]+x[8]+x[9]+x[10]+x[11],echo1,echo2,echo3,echo4,echo5,echo6,echo7,echo8,echo9,echo10,echo11,echo12);
        SynthEngine.audioModule=(new BilinearFunction(echo,preEcho,(x,y)->(x+y*.55)*.2));

        // SynthEngine.setAudioModule(q);

        // echo2.falloffFactor=new Constant(.9);
        // LinearFunction amplify=new LinearFunction(echo,x->.2*x);

        // SynthEngine.setAudioModule(TrilinearFunction.blend(echo,echo2,new Constant(.5)));
    }
}