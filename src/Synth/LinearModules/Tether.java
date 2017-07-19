package Synth.LinearModules;
public class Tether extends Filter
{
    public LinearModule tetherRadius;
    private double value;
    public double getSample(long Δↈsamples)
    {
        double in=input.getSample();
        double r=tetherRadius.getSample();
        value=Common.r.clamp(value,in-r,in+r);
        return value;
    }
    public Tether(LinearModule input)
    {
        super(input);
    }
    public Tether(LinearModule input,LinearModule tetherLength)
    {
        super(input);
    }
}
