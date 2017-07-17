public class Phasor extends Oscillator
{
    public Oscillator input;
    public LinearModule phase;
    public LinearModule phasorAmplitude;
    public Phasor(Oscillator input)
    {
        this.input=input;
        this.inputFrequency=new LinearModule()
        {
            double getSample()
            {
                return input.inputFrequency.getSample();
            }
        };
    }
    public Phasor(Oscillator input,LinearModule phase,LinearModule amplitude)
    {
        this.input=input;
        this.phase=phase;
        this.phasorAmplitude=amplitude;
        this.inputFrequency=new LinearModule()
        {
            double getSample()
            {
                return input.inputFrequency.getSample();
            }
        };
    }
    public double getSample(double ↈλ)
    {
        return input.getSample(ↈλ)+input.getSample((ↈλ+phase.getSample())%1)*phasorAmplitude.getSample();
    }
}
