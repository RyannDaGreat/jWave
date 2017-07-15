import javax.sound.sampled.Line;
public abstract class Oscillator extends Temporal
{
    public LinearModule inputFrequency=new Constant(440);
    private double ↈλ;//Between 0 and 1
    public final double getSample(long Δↈsamples)
    {
        ↈλ+=inputFrequency.getSample()*Δↈsamples/SynthEngine.SAMPLE_RATE;
        ↈλ%=1;
        return getSample(ↈλ);
    }
    public abstract double getSample(double ↈλ);
}
