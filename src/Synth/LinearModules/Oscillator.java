package Synth.LinearModules;
import Synth.SynthEngine;
public abstract class Oscillator extends Temporal
{
    public LinearModule inputFrequency=new Constant(440);
    public LinearModule inputAmplitude=new Constant(1);
    private double ↈλ;//Between 0 and 1
    public double getSample(long Δↈsamples)
    {
        ↈλ+=inputFrequency.getSample()*Δↈsamples/SynthEngine.SAMPLE_RATE;
        ↈλ%=1;
        return getSample(ↈλ)*inputAmplitude.getSample();
    }
    public abstract double getSample(double ↈλ);
}
