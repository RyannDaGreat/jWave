@SuppressWarnings("InfiniteLoopStatement")
public class Examples
{
    public static void main(String[]ⵁ)
    {
        // bitCrush();
        wtfcrazy();
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
        Reverb r=Reverb.effector(m);
        SynthEngine.setOutputModule(r);
    }
}
