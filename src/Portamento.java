public class Portamento extends Filter
{
    LinearModule αPerSecond;
    private double value;
    public Portamento(LinearModule input)
    {
        super(input);
    }
    public Portamento(LinearModule input,LinearModule αPerSecond)
    {
        super(input);
        this.αPerSecond=αPerSecond;
    }
    public double getSample(long Δↈsamples)
    {
        //Revvable Blend: https://www.desmos.com/calculator/5kye0k4fyv
        double newValue=input.getSample();
        value=Math.pow(αPerSecond.getSample(),(double)Δↈsamples/SynthEngine.SAMPLE_RATE)*(value-newValue)+newValue;
        return value;
    }
}
