// public class Limiter extends Filter
// {
//     public Limiter(LinearModule input)
//     {
//         super(input);
//     }
//     double max=1;
//     int N=100000;
//     int n=0;
//     public double getSample(long Δↈsamples)
//     {
//         double inputSample=input.getSample();
//         if(n++>N)
//         {
//             max*=.999;
//             n=0;
//         }
//         max=Math.max(inputSample,max);
//         max=Math.max(.1,max);
//         return inputSample/max;
//     }
// }
