public class BlendyEcho extends Filter
{
    private double[] history;
    private int cursor;
    public LinearModule alpha=new Constant(.5);
    public LinearModule lengthInSeconds=new Constant(.1);
    public double getSample(long Δↈsamples)
    {
        int lengthInSamples=Math.max(1,(int)(lengthInSeconds.getSample()*SynthEngine.SAMPLE_RATE));
        if(lengthInSamples!=history.length)
        {
            double[]temp=history;
            history=new double[lengthInSamples];
            for(int i=0;i<history.length;i++)
                history[i]=(int)(rOutpost.linterpCyclic((double)i/history.length*temp.length,temp));
        }
        for(int ⵁ=0;ⵁ<Δↈsamples;ⵁ++)
        {
            cursor++;
            cursor%=history.length;
            history[cursor]=rOutpost.blend(history[cursor],input.getSample(),alpha.getSample());
        }
        return history[cursor];
    }
    public BlendyEcho(LinearModule input,LinearModule lengthInSeconds,LinearModule alpha)
    {
        super(input);
        this.lengthInSeconds=lengthInSeconds;
        history=new double[(int)(SynthEngine.SAMPLE_RATE*lengthInSeconds.getSample())];
        this.alpha=alpha;
    }
}