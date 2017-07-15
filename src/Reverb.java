// import java.io.IOException;
// import java.util.ArrayList;
// public class Reverb extends Filter
// {
//     public double reverbMix=.5;
//     public double originalMix=.5;
//     public double xMod=1;
//     private int sampleRate;
//     private double[][] waveTable;
//     private int xl, yl;
//     private double reverbSampleTime;//In seconds of reverberation time
//     public Reverb(LinearModule input,double[][] waveTable,int sampleRate)
//     {
//         super(input);
//         this.waveTable=waveTable;
//         xl=waveTable.length;//The x-mod
//         yl=waveTable[0].length;//The wave itself
//         reverbSampleTime=(yl-1d)/sampleRate;//-1 for hypothetical safety..dunno if its nessecary but it cant hurt right
//         System.out.println("rst="+reverbSampleTime);
//         System.out.println("yl="+yl);
//         System.out.println("sr="+sampleRate);
//     }
//     private double getWaveTableSample(double timeInSeconds,double x)
//     {
//         return WaveCube.interpolateWaveTable(waveTable,x*(xl-1),timeInSeconds*sampleRate);
//     }
//     double inputPreviousSample;
//     double inputCurrentSample;
//     ArrayList<InputTimeDelta> inputTimeDeltas=new ArrayList<>();
//     class InputTimeDelta
//     {
//         double inputDelta;
//         double timeDelta;
//         long timeDeltaPicoseconds;
//         InputTimeDelta(double inputDelta,double timeDelta)
//         {
//             this.inputDelta=inputDelta;
//             this.timeDelta=timeDelta;
//             this.timeDeltaPicoseconds=(long)(timeDelta*10e12);
//         }
//     }
//     void timeStep(double deltaTime)
//     {
//         // input.timeStep(deltaTime);
//         inputPreviousSample=inputCurrentSample;
//         inputCurrentSample=input.getSample();
//         inputTimeDeltas.add(0,new InputTimeDelta(inputCurrentSample-inputPreviousSample,deltaTime));
//     }
//     double getSample()
//     {
//         double reverbComponent=0;
//         long totalPicoseconds=0;
//         ArrayList<InputTimeDelta> inputTimeDeltasToBeRemoved=new ArrayList<>();
//         for(InputTimeDelta inputTimeDelta : inputTimeDeltas)
//         {
//             if(totalPicoseconds/10e12<reverbSampleTime)
//             {
//                 reverbComponent+=getWaveTableSample(totalPicoseconds/10e12,xMod)*inputTimeDelta.inputDelta;
//                 totalPicoseconds+=inputTimeDelta.timeDeltaPicoseconds;
//             }
//             else
//             {
//                 System.out.println("chee");
//                 inputTimeDeltasToBeRemoved.add(inputTimeDelta);
//             }
//         }
//         if(r.toc()>1)
//         {
//             System.out.println(totalPicoseconds);
//             r.ptoc();
//             r.tic();
//         }
//         for(InputTimeDelta inputTimeDelta : inputTimeDeltasToBeRemoved)
//         {
//             inputTimeDeltas.remove(inputTimeDelta);
//         }
//         // return reverbComponent*reverbMix+originalMix*inputCurrentSample;
//         return inputCurrentSample;
//     }
//     public static void main(String[] ih
//                            ) throws IOException
//     {
//     }
// }
