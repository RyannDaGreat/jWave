public abstract class Oscillator extends AudioModule
{
    public static final double defaultFrequency=440;
    private double frequency=defaultFrequency;
    /**
     * @param frequency In Hz
     */
    public void setFrequency(double frequency)
    {
        this.frequency=frequency;
    }
    public double getFrequency()
    {
        return frequency;
    }
    public void setPitch(double semitones)
    {
        setFrequency(defaultFrequency*Math.pow(2,semitones/12));
    }
    public double getPitch()
    {
        return 12*r.log(2,frequency/defaultFrequency);
    }
}
