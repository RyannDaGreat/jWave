public class Sawtooth extends Oscillator
{
    double λ;//-1 ≤ λ ≤ 1 Δλ
    double getSample()
    {
        return 2*λ-1;
    }
    double t=0;
    void timeStep(double deltaTime)
    {
        double f=getFrequency();
        double Δt=deltaTime;
        double Δλ=f*Δt;
        λ+=Δλ;
        t+=Δλ;
        λ%=1;

    }
}
