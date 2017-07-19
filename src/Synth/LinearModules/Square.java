package Synth.LinearModules;
import Common.r;
public class Square extends Oscillator
{
    public double getSample(double ↈλ)
    {
        return r.sign(ↈλ*2-1);
    }
}
