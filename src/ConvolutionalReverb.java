import java.io.IOException;
public class ConvolutionalReverb extends Filter
{
    //WARNING: MINDFUCKINGLY SLOW!!
    //IMPORTANT: NOT INVARIANT TO SAMPLERATE RIGHT NOW!
    //IMPORTANT: REVERB ASSUMES THAT THE SAMPLERATE OF THE REVERB SAMPLE IS THE SAME AS THE SYNTH!!!
    public LinearModule reverbMix=new Constant(100);
    public LinearModule originalMix=new Constant(100);
    public LinearModule xMod=new Constant(.5);
    private double[][] waveTable;
    private int xl, yl;
    private double[] inputDeltaHistory;
    private int inputHistoryCursor=0;
    public ConvolutionalReverb(LinearModule input,double[][] waveTable)
    {
        super(input);
        this.waveTable=waveTable;
        xl=waveTable.length;//The x-mod
        yl=waveTable[0].length/10;//The wave itself
        inputDeltaHistory=new double[yl];
    }
    private double getWaveTableSample(double ↈsamples)
    {
        double v=WaveCubestuffs.interpolateWaveTable(waveTable,xMod.getSample()*(xl-1),ↈsamples%yl);
        // System.out.println(v);
        return v;
    }
    private double inputCurrentSample;
    public double getSample(long Δↈsamples)
    {

        double inputPreviousSample=inputCurrentSample;
        inputCurrentSample=input.getSample();
        // System.out.println(inputCurrentSample);
        // return inputCurrentSample;
        inputHistoryCursor--;
        if(inputHistoryCursor<0)
            inputHistoryCursor=yl-1;
        inputDeltaHistory[inputHistoryCursor]=(inputCurrentSample-inputPreviousSample)*Δↈsamples;
        //
        double reverbComponent=0;
        for(int i=0;i<yl;i+=1)
        {
            reverbComponent+=inputDeltaHistory[(inputHistoryCursor+i)%yl]*getWaveTableSample(i);
        }
        // System.out.println(reverbComponent*100);
        // System.out.println(reverbComponent);
        // return inputCurrentSample;
        return reverbComponent;//*reverbMix.getSample()+inputCurrentSample*originalMix.getSample();
    }
    public static ConvolutionalReverb effector(LinearModule input)
    {
        double[][] doubles=null;
        try
        {
            doubles=WaveCubestuffs.loadWaveTable("/Users/Ryan/Desktop/RyanCourseSiteGeneratorThirdRecovery/Digilin/FromEffector/EffectorReverb2.png");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return new ConvolutionalReverb(input,doubles);
    }
}
