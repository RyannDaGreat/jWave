public class LinearFunction extends LinearModule
{
    LinearModule input=null;
    private final java.util.function.Function<Double,Double> function;
    public LinearFunction(java.util.function.Function<Double,Double>function)
    {
        this.function=function;
    }
    public LinearFunction(LinearModule input,java.util.function.Function<Double,Double>function)
    {
        this.input=input;
        this.function=function;
    }
    public double getSample()
    {
        return function.apply(input.getSample());
    }
}
