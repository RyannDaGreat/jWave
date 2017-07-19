package Synth.LinearModules;
import java.util.function.BiFunction;
public class BilinearFunction extends LinearModule
{
    public LinearModule input1=null;
    public LinearModule input2=null;
    private final BiFunction<Double,Double,Double> function;
    public BilinearFunction(BiFunction<Double,Double,Double>function)
    {
        this.function=function;
    }
    public BilinearFunction(LinearModule input1,LinearModule input2,BiFunction<Double,Double,Double>function)
    {
        this.input1=input1;
        this.input2=input2;
        this.function=function;
    }
    public double getSample()
    {
        return function.apply(input1.getSample(),input2.getSample());
    }
    //region Toolbox
    public static BilinearFunction multiply(LinearModule input1,LinearModule input2)
    {
        return new BilinearFunction(input1,input2,(x,y)->x*y);
    }
    public static BilinearFunction add(LinearModule input1,LinearModule input2)
    {
        return new BilinearFunction(input1,input2,(x,y)->x+y);
    }
    public static BilinearFunction subtract(LinearModule input1,LinearModule input2)
    {
        return new BilinearFunction(input1,input2,(x,y)->x-y);
    }
    public static BilinearFunction divide(LinearModule input1,LinearModule input2)//Returns 0 if divide by 0
    {
        return new BilinearFunction(input1,input2,(x,y)->y==0?0:x/y);
    }
    public static BilinearFunction pow(LinearModule input1,LinearModule input2)//Returns 0 if divide by 0
    {
        return new BilinearFunction(input1,input2,Math::pow);
    }
    //endregion
}
