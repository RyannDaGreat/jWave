package Synth.LinearModules;
public class PolylinearFunction extends LinearModule
{
    public interface PolyFunction<A,B>
    {
        A a(B[] b);
    }
    public LinearModule[] inputs=null;
    private final PolyFunction<Double,Double> function;
    public PolylinearFunction(PolyFunction<Double,Double> function,LinearModule... inputs)
    {
        this.inputs=inputs;
        this.function=function;
    }
    public double getSample()
    {
        Double[] doubles=new Double[inputs.length];
        int i=0;
        for(LinearModule input : inputs)
        {
            doubles[i++]=input.getSample();
        }
        return function.a(doubles).doubleValue();
    }
}
