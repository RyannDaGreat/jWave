public class WaveCubeReader extends Sawtooth
{
    double getSample()
    {
        return WaveCube.waveCubeSample(λ,getFrequency(),x);//Math.sin(660*t/SynthTest.SAMPLE_RATE)/2+.5);
        // return WaveCube.waveCubeSample(λ,getFrequency(),Math.sin(660*t/SynthTest.SAMPLE_RATE)/2+.5);
    }
    double x=.5;
    public void setX(double x)
    {
        this.x=x;
    }
}
