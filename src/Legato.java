public class Legato extends Filter
{
    LinearModule αPerSecond;
    private double value;
    public Legato(LinearModule input)
    {
        super(input);
    }
    public Legato(LinearModule input,LinearModule αPerSecond)
    {
        super(input);
        this.αPerSecond=αPerSecond;
    }
    public double getSample(long Δↈsamples)
    {
        //Revvable Blend: https://www.desmos.com/calculator/5kye0k4fyv
        double newValue=input.getSample();
        value=Math.pow(1-αPerSecond.getSample(),(double)Δↈsamples/SynthEngine.SAMPLE_RATE)*(value-newValue)+newValue;
        return value;
    }
}
