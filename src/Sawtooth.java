public class Sawtooth extends Oscillator
{
    private double λ;//-1 ≤ λ ≤ 1 Δλ
    double getSample()
    {
        return 2*λ-1;
    }
    void timeStep(double deltaTime)
    {
        double f=getFrequency();
        double Δt=deltaTime;
        double Δλ=f*Δt;
        λ+=Δλ;
        λ%=1;
    }
}
