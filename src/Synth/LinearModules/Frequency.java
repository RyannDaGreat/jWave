package Synth.LinearModules;
import Common.r;
public class Frequency extends Constant
{
    public Frequency()
    {
    }
    public Frequency(double frequency)
    {
        setFrequency(frequency);
    }
    public void setFrequency(double frequency)
    {
        setValue(frequency);
    }
    public double getFrequency()
    {
        return getValue();
    }
    private static final double centerFrequency=440;//Frequency when pitch is 0
    public void setPitch(double ↈsemitones)
    {
        setFrequency(centerFrequency*Math.pow(2,ↈsemitones/12));
    }
    public double getPitch()
    {
        return 12*r.log(2,getFrequency()/centerFrequency);
    }
}
