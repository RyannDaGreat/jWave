import java.io.IOException;
import java.util.ArrayList;
public class Reverb extends Filter
{
    public double reverbMix=.5;
    public double originalMix=.5;


    private int sampleRate;
    private double[][]waveTable;
    private int xl,yl;
    private double sampleLength;//In seconds of reverberation time
    public Reverb(LinearModule input,double[][]waveTable,int sampleRate)
    {
        super(input);
        this.waveTable=waveTable;
        xl=waveTable.length;//The x-mod
        yl=waveTable[0].length;//The wave itself
        sampleLength=sampleRate*yl;
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
        inputTimeDeltas.add(new InputTimeDelta(inputCurrentSample-inputPreviousSample,deltaTime));
    }
    double getSample()
    {
        double out=0;
        out+=inputCurrentSample*originalMix;
        double time=0;
        for(InputTimeDelta inputTimeDelta : inputTimeDeltas)
        {
            if()
            time+=inputTimeDelta.timeDelta;
        }
    }
    public static void main(String[]ih
                           ) throws IOException
    {

        new Reverb(new Sawtooth(),WaveCube.loadWaveTable("/Users/Ryan/Desktop/RyanCourseSiteGeneratorThirdRecovery/Digilin/FromEffector/EffectorReverb.png"),44100);
    }
}
