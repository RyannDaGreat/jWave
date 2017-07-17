public abstract class Temporal extends LinearModule
{
    private long oldↈSamples;
    private double sample;
    public final double getSample()
    {
        final long newↈSamples=SynthEngine.getCurrentↈSamples();
        if(newↈSamples!=oldↈSamples)
        {
            assert newↈSamples>oldↈSamples:"A lot of code written in Temporal modules relies on the fact that Δↈsamples ∈ ℕ⁺  If this assertion is triggered it means that, somehow, time appears to have went backwards.";
            sample=getSample(newↈSamples-oldↈSamples);
            oldↈSamples=newↈSamples;
        }
        return sample;
    }
    public abstract double getSample(long Δↈsamples);
}
