package Synth.LinearModules;
import Common.r;
public class Triangle extends Oscillator
{
    public double getSample(double ↈλ)
    {
        return r.asin(r.sin(r.τ*ↈλ))*2/r.π;
    }
}
