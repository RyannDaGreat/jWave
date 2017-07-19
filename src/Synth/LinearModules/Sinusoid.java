package Synth.LinearModules;
import Common.r;
public class Sinusoid extends Oscillator
{
    public double getSample(double ↈλ)
    {
        return r.sin(r.τ*ↈλ);
    }
}
