public class Triangle extends Oscillator
{
    public double getSample(double ↈλ)
    {
        return r.asin(r.sin(rOutpost.τ*ↈλ))*2/rOutpost.π;
    }
}
