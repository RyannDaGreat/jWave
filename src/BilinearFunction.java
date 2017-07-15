import java.util.DoubleSummaryStatistics;
public class BilinearFunction extends LinearModule
{
    LinearModule input1=null;
    LinearModule input2=null;
    private final java.util.function.BiFunction<Double,Double,Double> function;
    public BilinearFunction(java.util.function.BiFunction<Double,Double,Double>function)
    {
        this.function=function;
    }
    public BilinearFunction(LinearModule input1,LinearModule input2,java.util.function.BiFunction<Double,Double,Double>function)
    {
        this.input1=input1;
        this.input2=input2;
        this.function=function;
    }
    public double getSample()
    {
        return function.apply(input1.getSample(),input2.getSample());
    }
}
