package Synth.LinearModules;
import Synth.SynthEngine;
public class GetTime extends LinearModule
{
    public double getSample()
    {
        return SynthEngine.getCurrentTime();
    }
}
