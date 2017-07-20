package Synth.LinearModules;
public class Constant extends LinearModule
{
    public double value;
    public double getSample()
    {
        return value;
    }
    public Constant()
    {
    }
    public Constant(double value)
    {
        this.value=value;
    }
}
