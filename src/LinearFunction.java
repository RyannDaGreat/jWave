import java.util.function.Function;
public class LinearFunction extends LinearModule
{
    LinearModule input=null;
    private final Function<Double,Double> function;
    public LinearFunction(Function<Double,Double>function)
    {
        this.function=function;
    }
    public LinearFunction(LinearModule input,Function<Double,Double>function)
    {
        this.input=input;
        this.function=function;
    }
    public double getSample()
    {
        return function.apply(input.getSample());
    }
    public static LinearFunction pitchToFrequency(LinearModule input)
    {
        return new LinearFunction(input,(ↈsemitones)->440*Math.pow(2,ↈsemitones/12));
    }
    public static LinearFunction frequencyToPitch(LinearModule input)
    {
        return new LinearFunction(input,(frequency)->12*r.log(2,frequency/440));
    }
}
