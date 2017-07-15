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
}
