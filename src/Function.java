public class Function extends LinearModule
{
    LinearModule input=null;
    private final java.util.function.Function<Double,Double> function;
    public Function(java.util.function.Function<Double,Double>function)
    {
        this.function=function;
    }
    public Function(LinearModule input,java.util.function.Function<Double,Double>function)
    {
        this.input=input;
        this.function=function;
    }
    public double getSample()
    {
        return function.apply(input.getSample());
    }
}
