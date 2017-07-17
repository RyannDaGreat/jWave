@SuppressWarnings("InfiniteLoopStatement")
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
        SynthEngine.setOutputModule(sr);
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
        SynthEngine.setOutputModule(wc);
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
        SynthEngine.setOutputModule(r);
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
        SynthEngine.setOutputModule(gate);
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
        SynthEngine.setOutputModule(sr);
    }
    public static void phasorTest()
    {
        Oscillator modulin=new Sawtooth();
        Phasor p=new Phasor(modulin,new GetTime(),new Constant(1));
        Phasor p2=new Phasor(p,new LinearFunction(new GetTime(),x->1.565436*x+.2),new Constant(.7));
        Phasor p3=new Phasor(p2,new LinearFunction(new GetTime(),x->1.1231*x+.2),new Constant(.7));
        p3.inputFrequency=new Constant(100);
        SynthEngine.setOutputModule(new LinearFunction(p3,x->x*.2));
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
        SynthEngine.setOutputModule(amplify);
    }
}
