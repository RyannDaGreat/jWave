public class Echo extends Filter
{
    private final double[] history;
    private int cursor;
    public LinearModule falloffFactor=new Constant(.5);
    public double getSample(long Δↈsamples)
    {
        final double inputSample=input.getSample();
        final double falloff=falloffFactor.getSample();
        for(int ⵁ=0;ⵁ<Δↈsamples;ⵁ++)
        {
            cursor++;
            cursor%=history.length;
            history[cursor]*=falloff;
            history[cursor]+=inputSample;
        }
        return history[cursor];
    }
    //region Constructors
    public Echo(LinearModule input,double numberOfSeconds)
    {
        super(input);
        history=new double[(int)(SynthEngine.SAMPLE_RATE*numberOfSeconds)];
    }
    public Echo(LinearModule input,double numberOfSeconds,LinearModule falloffFactor)
    {
        super(input);
        history=new double[(int)(SynthEngine.SAMPLE_RATE*numberOfSeconds)];
        this.falloffFactor=falloffFactor;
    }
    //endregion
}
