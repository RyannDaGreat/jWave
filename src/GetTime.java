public class GetTime extends LinearModule
{
    double getSample()
    {
        return SynthEngine.getCurrentTime();
    }
}
