package Synth.LinearModules;
import Common.r;
public class TrilinearFunction extends LinearModule
{
    interface TriFunction<A,B,C,D>
    {
        A a(B b,C c,D d);
    }
    public LinearModule input1=null;
    public LinearModule input2=null;
    public LinearModule input3=null;
    private final TrilinearFunction.TriFunction<Double,Double,Double,Double> function;
    public TrilinearFunction(TrilinearFunction.TriFunction<Double,Double,Double,Double> function)
    {
        this.function=function;
    }
    public TrilinearFunction(LinearModule input1,LinearModule input2,LinearModule input3,TrilinearFunction.TriFunction<Double,Double,Double,Double> function)
    {
        this.input1=input1;
        this.input2=input2;
        this.input3=input3;
        this.function=function;
    }
    public double getSample()
    {
        return function.a(input1.getSample(),input2.getSample(),input3.getSample());
    }
    //region Toolbox
    public static TrilinearFunction blend(LinearModule x,LinearModule y,LinearModule α)
    {
        return new TrilinearFunction(x,y,α,r::blend);
    }
    //endregion
}
