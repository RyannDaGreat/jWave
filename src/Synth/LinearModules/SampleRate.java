package Synth.LinearModules;
import Synth.SynthEngine;
public class SampleRate extends Filter
{
    public LinearModule sampleRateInHz;
    public SampleRate(LinearModule input)
    {
        super(input);
    }
    public SampleRate(LinearModule input,LinearModule sampleRateInHz)
    {
        super(input);
        this.sampleRateInHz=sampleRateInHz;
    }
    private long timeInPicoseconds=1_000_000_000_000_000_000L;//Thus it must immediately trigger the first time
    private double value;
    public double getSample(long Δↈsamples)
    {
        double sampleRate=sampleRateInHz.getSample();
        timeInPicoseconds+=1_000_000_000_000L*Δↈsamples/SynthEngine.SAMPLE_RATE;
        if(sampleRate!=0&&timeInPicoseconds>=1e12/sampleRate)
        {
            value=input.getSample();
            timeInPicoseconds=0;
        }
        return value;
    }
}
