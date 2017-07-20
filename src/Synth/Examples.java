package Synth;
import Common.r;
import Synth.LinearModules.*;
@SuppressWarnings({"InfiniteLoopStatement","Duplicates"})
public class Examples
{
    public static void main(String[]ⵁ)
    {
        // bitCrush();
        wtfCrazyWithRealCuttoff();
    }
    public static void bitCrush()
    {
        Oscillator saw=new Sinusoid();
        Frequency sawFrequency=new Frequency();
        saw.inputFrequency=sawFrequency;
        SampleRate sr=new SampleRate(saw,new Frequency(800));
        SynthEngine.audioModule=sr;
        while(true)
            sawFrequency.setPitch(-12+Math.sin(r.toc()*.25)*12+Math.sin(r.toc()*.3)*Math.sin(r.toc()*.3)*(Math.acos(Math.cos(r.toc()*50)))*.5);
    }
    public static void wtfcrazy()
    {
        WaveCube wc=WaveCube.modulinOscillator();
        Sinusoid sin=new Sinusoid();
        wc.inputFrequency=new Constant(110);
        sin.inputFrequency=new LinearFunction(LinearFunction.pitchToFrequency(new GetTime()),x->x*.1-3);
        wc.xModInput=new LinearFunction(sin,x->x*x/2.4);//LinearFunction.pitchToFrequency(BilinearFunction.multiply(sin,BilinearFunction.add(sin,new Constant(-10))));
        SynthEngine.audioModule=wc;
    }
    public static void sadLittleReverb()
    {
        WaveCube wc=WaveCube.modulinOscillator();
        WaveCube wc2=WaveCube.modulinOscillator();
        Square s=new Square();
        s.inputFrequency=new Constant(1);
        wc.inputFrequency=new Constant(100);
        LinearFunction gate=new LinearFunction(s,x->x/2+.5);
        wc2.inputFrequency=new Frequency(441);
        BilinearFunction m=BilinearFunction.multiply(wc,gate);
        ConvolutionalReverb r=ConvolutionalReverb.effector(m);
        SynthEngine.audioModule=r;
    }
    public static void wtfCrazyWithRealCuttoff()
    {
        // Oscillator wc=WaveCube.modulinOscillator();
        Oscillator wc=new Square();
        // Square s=new Square();
        // s.inputFrequency=new Constant(1);
        wc.inputFrequency=new Constant(440);
        Sinusoid sin=new Sinusoid();
        sin.inputFrequency=LinearFunction.pitchToFrequency(new GetTime());
        LinearModule sin2=new LinearFunction(BilinearFunction.multiply(BilinearFunction.multiply(sin,sin),new Constant(1000)),x->Math.pow(2,-x));
        // LinearFunction input=new LinearFunction(s,x->x/2+.5);
        LinearModule gate=new Portamento(wc,sin2);
        // BilinearFunction m=BilinearFunction.multiply(wc,gate);
        SynthEngine.audioModule=gate;
    }
    public static void aThing()
    {
        WaveCube modulin=WaveCube.modulinOscillator();
        LinearModule modulinPitch=new Constant(0);
        modulin.inputFrequency=LinearFunction.pitchToFrequency(modulinPitch);
        Oscillator saw=WaveCube.modulinOscillator();
        Oscillator sin=new Triangle();
        saw.inputFrequency=LinearFunction.pitchToFrequency(new LinearFunction(sin,x->x*6-12));
        sin.inputFrequency=LinearFunction.pitchToFrequency(new LinearFunction(new GetTime(),x->x-100));
        SampleRate sr=new SampleRate(saw,BilinearFunction.add(sin.inputFrequency,new LinearFunction(saw.inputFrequency,x->x*1.3)));
        SynthEngine.audioModule=sr;
    }
    public static void phasorTest()
    {
        Oscillator modulin=new Sawtooth();
        Phasor p=new Phasor(modulin,new GetTime(),new Constant(1));
        Phasor p2=new Phasor(p,new LinearFunction(new GetTime(),x->1.565436*x+.2),new Constant(.7));
        Phasor p3=new Phasor(p2,new LinearFunction(new GetTime(),x->1.1231*x+.2),new Constant(.7));
        p3.inputFrequency=new Constant(100);
        SynthEngine.audioModule=new LinearFunction(p3,x->x*.2);
    }
    public static void prettyMajorScale()
    {
        WaveCube modulin=WaveCube.modulinOscillator();
        modulin.xModInput=new Constant(.3);
        Triangle triangle=new Triangle();
        triangle.inputFrequency=new Constant(.1);
        LinearFunction majorScalePitchArpeggio=LinearFunction.majorNoteToPitch(LinearFunction.round(new LinearFunction(triangle,x->x*9-8)));
        Triangle pitchVibrato=new Triangle();
        pitchVibrato.inputFrequency=new Constant(7);
        pitchVibrato.inputAmplitude=new Constant(.5);
        modulin.inputFrequency=LinearFunction.pitchToFrequency(BilinearFunction.add(new Portamento(majorScalePitchArpeggio,new Constant(.00000000000000000000001)),pitchVibrato));
        Phasor phasor=new Phasor(modulin,new GetTime(),new Constant(-.2));
        Portamento lowPass=new Portamento(phasor,new Constant(1e-30));
        TrilinearFunction blend=TrilinearFunction.blend(phasor,lowPass,new Constant(.8));
        LinearFunction amplify=new LinearFunction(blend,x->1.4*x);
        SynthEngine.audioModule=amplify;
    }
    public static void reverbViaEcho()
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
        double t=.051;
        double alpha=.25;
        HardcodedConvolutionalReverb q=new HardcodedConvolutionalReverb(blend);
        Square square=new Square();
        square.inputFrequency=new Constant(.1);
        LinearFunction gate=new LinearFunction(square,(x)->(x+1)/2);
        LinearModule preEcho=BilinearFunction.multiply(blend,gate);//new BlendyEcho(q,.01,new Constant(.2));
        LinearModule echo1=new BlendyEcho(preEcho,t/1+.04,new Constant(alpha/1));//a
        LinearModule echo2=new BlendyEcho(preEcho,t/2+.04,new Constant(alpha/2));//b+a
        LinearModule echo3=new BlendyEcho(preEcho,t/3+.04,new Constant(alpha/3));//c+a
        LinearModule echo4=new BlendyEcho(preEcho,t/4+.04,new Constant(alpha/4));//b+d+a
        LinearModule echo5=new BlendyEcho(preEcho,t/5+.04,new Constant(alpha/5));//e+a
        LinearModule echo6=new BlendyEcho(preEcho,t/6+.04,new Constant(alpha/6));//a+c+b+f
        LinearModule echo7=new BlendyEcho(preEcho,t/7+.04,new Constant(alpha/7));//a+g
        LinearModule echo8=new BlendyEcho(preEcho,t/8+.04,new Constant(alpha/8));//a+g
        LinearModule echo9=new BlendyEcho(preEcho,t/9+.04,new Constant(alpha/9));//a+g
        LinearModule echo10=new BlendyEcho(preEcho,t/10+.04,new Constant(alpha/10));//a+g
        LinearModule echo11=new BlendyEcho(preEcho,t/11+.04,new Constant(alpha/11));//a+g
        LinearModule echo12=new BlendyEcho(preEcho,t/12+.04,new Constant(alpha/12));//a+g
        PolylinearFunction echo=new PolylinearFunction(x->x[0]+x[1]+x[2]+x[3]+x[4]+x[5]+x[6]+x[7]+x[8]+x[9]+x[10]+x[11],echo1,echo2,echo3,echo4,echo5,echo6,echo7,echo8,echo9,echo10,echo11,echo12);
        SynthEngine.audioModule=(new BilinearFunction(echo,preEcho,(x,y)->(x+y*.5)*.1));
    }
}
