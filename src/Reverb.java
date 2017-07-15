import java.io.IOException;
import java.util.ArrayList;
public class Reverb extends Filter
{
    public double reverbMix=.5;
    public double originalMix=.5;
    public double xMod=.5;

    private int sampleRate;
    private double[][]waveTable;
    private int xl,yl;
    private double reverbSampleTime;//In seconds of reverberation time
    public Reverb(LinearModule input,double[][]waveTable,int sampleRate)
    {
        super(input);
        this.waveTable=waveTable;
        xl=waveTable.length;//The x-mod
        yl=waveTable[0].length;//The wave itself
        reverbSampleTime=sampleRate*(yl-1);//-1 for hypothetical safety..dunno if its nessecary but it cant hurt right
    }
    private double getWaveTableSample(double timeInSeconds,double x)
    {
        return WaveCube.interpolateWaveTable(waveTable,x*(xl-1),timeInSeconds*sampleRate);
    }
    double inputPreviousSample;
    double inputCurrentSample;

    ArrayList<InputTimeDelta> inputTimeDeltas;
    class InputTimeDelta
    {
        double inputDelta;
        double timeDelta;
        InputTimeDelta(double inputDelta,double timeDelta)
        {
            this.inputDelta=inputDelta;
            this.timeDelta=timeDelta;
        }
    }
    void timeStep(double deltaTime)
    {
        input.timeStep(deltaTime);
        inputPreviousSample=inputCurrentSample;
        inputCurrentSample=input.getSample();
        inputTimeDeltas.add(0,new InputTimeDelta(inputCurrentSample-inputPreviousSample,deltaTime));
    }
    double getSample()
    {
        double reverbComponent=0;
        double totalTime=0;
        for(InputTimeDelta inputTimeDelta : inputTimeDeltas)
        {
            if(totalTime<reverbSampleTime)
            {
                reverbComponent+=getWaveTableSample(totalTime,xMod)*inputTimeDelta.inputDelta;
                totalTime+=inputTimeDelta.timeDelta;
            }
            else
            {
                inputTimeDeltas.remove(inputTimeDelta);
            }
        }
        return reverbComponent*reverbMix+originalMix*inputCurrentSample;
    }
    public static void main(String[]ih
                           ) throws IOException
    {

    }
}
