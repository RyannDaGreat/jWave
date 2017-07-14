public abstract class LinearModule
{
    abstract double getSample();
    /**
     * @param deltaTime In seconds
     */
    abstract void timeStep(double deltaTime);
    public double[]getDoubleBuffer(int numberOfSamples,double sampleRate)
    {
        double[]buffer=new double[numberOfSamples];
        for(int i=0;i<numberOfSamples;i++)
        {
            buffer[i]=getSample();
            timeStep(1/sampleRate);
        }
        return buffer;
    }
    public byte[]get16BitBuffer(int numberOfSamples,double sampleRate)
    {
        return rOutpost.doublesTo16BitAudioBytes(getDoubleBuffer(numberOfSamples,sampleRate));
    }
    public byte[]get8BitBuffer(int numberOfSamples,double sampleRate)
    {
        return rOutpost.doublesTo8BitAudioBytes(getDoubleBuffer(numberOfSamples,sampleRate));
    }
}
