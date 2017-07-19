package Synth.LinearModules;
import Common.r;
import Synth.SynthEngine;
public class BlendyEcho extends Filter
{
    private final double[] history;
    private int cursor;
    public LinearModule alpha=new Constant(.5);
    public double getSample(long Δↈsamples)
    {
        for(int ⵁ=0;ⵁ<Δↈsamples;ⵁ++)
        {
            cursor++;
            cursor%=history.length;
            history[cursor]=r.blend(history[cursor],input.getSample(),alpha.getSample());
        }
        return history[cursor];
    }
    //region Constructors
    public BlendyEcho(LinearModule input,double numberOfSeconds)
    {
        super(input);
        history=new double[(int)(SynthEngine.SAMPLE_RATE*numberOfSeconds)];
    }
    public BlendyEcho(LinearModule input,double numberOfSeconds,LinearModule alpha)
    {
        super(input);
        history=new double[(int)(SynthEngine.SAMPLE_RATE*numberOfSeconds)];
        this.alpha=alpha;
    }
    //endregion
}
