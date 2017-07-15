public abstract class Temporal extends LinearModule
{
    private long oldↈSamples;
    private double sample;
    public final double getSample()
    {
        final long newↈSamples=SynthEngine.getCurrentↈSamples();
        if(newↈSamples!=oldↈSamples)
        {
            sample=getSample(newↈSamples-oldↈSamples);
            oldↈSamples=newↈSamples;
        }
        return sample;
    }
    public abstract double getSample(long Δↈsamples);
}
