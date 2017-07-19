package Synth.LinearModules;
import Common.r;

import java.util.function.Function;
public class LinearFunction extends LinearModule
{
    public LinearModule input=null;
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
    public static LinearFunction round(LinearModule input)
    {
        return new LinearFunction(input,x->(double)Math.round(x));
    }
    public static LinearFunction floor(LinearModule input)
    {
        return new LinearFunction(input,Math::floor);
    }
    public static LinearFunction ceil(LinearModule input)
    {
        return new LinearFunction(input,Math::ceil);
    }
    public static LinearFunction sign(LinearModule input)
    {
        return new LinearFunction(input,Math::signum);
    }
    public static LinearFunction sin(LinearModule input)
    {
        return new LinearFunction(input,Math::sin);
    }
    public static LinearFunction cos(LinearModule input)
    {
        return new LinearFunction(input,Math::cos);
    }
    public static LinearFunction squared(LinearModule input)
    {
        return new LinearFunction(input,x->x*x);
    }
    public static LinearFunction noteToPitch(LinearModule input,double...fullOctaveOfSemitonesInclusive)
    {
        return new LinearFunction(input,x->r.linterpCyclicCumulative(x,fullOctaveOfSemitonesInclusive));
    }
    public static LinearFunction majorNoteToPitch(LinearModule input)
    {
        return noteToPitch(input,0,2,4,5,7,9,11,12);
    }
}
