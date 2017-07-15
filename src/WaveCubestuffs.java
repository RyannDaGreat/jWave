import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
@SuppressWarnings("SuspiciousNameCombination")
public class WaveCubestuffs
{
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException
    {
        // Color[][] colors=loadPixelsFromImage(new File("/Users/Ryan/Desktop/scrunge.png.png"));
        // System.out.println("Color[0][0] = "+colors[0][0].getRGBComponents(null)[0]);
        // System.out.println(java.util.Arrays.deepToString(loadWaveTable("/Users/Ryan/Desktop/scrunge.png.png")));
        double[][] doubles=waveTable("/Users/Ryan/Desktop/ReverbKernels/FromEffector/Kernel",23);
        System.out.println(doubles[0].length);
        // System.out.println(java.util.Arrays.deepToString(doubles));
    }
    //region For reverb effect
    public static double[][]waveTable(String filePathPrefix,int numberOfWaves) throws IOException, UnsupportedAudioFileException
    {
        double[][]out=new double[numberOfWaves][];
        for(int i=0;i<numberOfWaves;i++)
        {
            out[i]=new WaveFile(new File(filePathPrefix+numberOfWaves+".wav")).asDoubleArray();
        }
        return out;
    }
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static double interpolateWaveTable(double[][]waveTable,double x,double y)//x and y ∈ [0,1)
    {
        int xl=waveTable.length;
        int yl=waveTable[0].length;
        int xf=(int)r.floor(x);
        int yf=(int)r.floor(y);
        int yc=(int)r.ceil(y);
        int xc=(int)r.ceil(x);
        double x0=rOutpost.lerp(x,xf,xc,waveTable[xf%xl][yf%yl],waveTable[xc%xl][yf%yl]);
        double x1=rOutpost.lerp(x,xf,xc,waveTable[xf%xl][yc%yl],waveTable[xc%xl][yc%yl]);
        double xy=rOutpost.lerp(y,yf,yc,x0,x1);
        // System.out.println(xy);
        return xy;
    }
    //endregion
    private static int[][] transposeMatrix(int[][] m)
    {
        int[][] temp=new int[m[0].length][m.length];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
            {
                temp[j][i]=m[i][j];
            }
        }
        return temp;
    }
    public static double[][] loadWaveTable(String imagePath) throws IOException
    {
        File file=new File(imagePath);
        BufferedImage img=ImageIO.read(file);
        int width=img.getWidth();
        int height=img.getHeight();
        int[][] imgArr=new int[width][height];
        Raster raster=img.getData();
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                imgArr[i][j]=raster.getSample(i,j,0);
            }
        }
        imgArr=transposeMatrix(imgArr);//To match python's output
        //Convert it to a double array of doubles ∈ [0,1]:
        double[][] out=new double[imgArr.length][imgArr[0].length];
        for(int i=0;i<out.length;i++)
        {
            for(int j=0;j<out[0].length;j++)
            {
                out[i][j]=Math.min(1,(imgArr[i][j]-127)/127d);
            }
        }
        return out;
    }
}
//region Python code to make a set of wave-table images (used to make a wavecube)
// from r import *
// lsf=load_sound_file
// lg=line_graph
// ps=play_sound_from_samples
// i=np.interp
// asp=np.array_split
// file="/Applications/FL.app/Contents/Resources/drive_c/Program Files/Image-Line/FL Studio 12/Data/Projects/ModuloiSamplern_2.wav"
// s=lsf(file)
// nom=number_of_megachunks=21#Different X-values in harmor
// megachunks=asp(s,nom)
// samplerate=sr=44100
// di=display_image
//
// def wavetable(s):
//     m=np.mean(s,1)
//     L=len(m)
//     #Assumption: There are 132 notes in FL studio so this shuld be divided into 132 parts
//     mt=middle_third=lambda l:l[len(l)//3:len(l)-len(l)//3]
//     chunks=[m[int(n*L/132):int((n+1)*L/132)]for n in range(132)]
//     ch=chunks=list(map(mt,chunks))
//     cf=chunkfreqs=[440*2**((n-57)/12)for n in range(132)]
//     def wavefunction(N):return lambda wavelengths:np.interp(wavelengths*(sr / chunkfreqs [ N ] * 2),range(len(chunks[N])),chunks[N],period=(sr / chunkfreqs [ N ] * 2))
//     cf=chunkfunctions=[wavefunction(n)for n in range(132)]
//     #lg(cf[55](np.linspace(0,3,1000)))
//
//
//
//     #Next step: Take this turn it into matrix multiplication to extract frequencies
//     #Step after that: Take polynomial regression of 3 variables to 2 variables: Harmonic #, Pitch, X-Mod to Harmonic Amplitude and Harmonic Phase (subtracting the fundamental's phase)
//
//     def harmonic_analysis(wave,harmonics:int):
//         prod=np.matmul
//         inv=np.linalg.inv
//         b=wave#In terms of linear algebra in Ax~=b
//         lg=line_graph
//         di=display_image
//         samples=len(b)
//         m=np.asmatrix(np.linspace(1,harmonics,harmonics)).T*np.matrix(np.linspace(0,tau,samples,endpoint=False))
//         A=np.asmatrix(np.concatenate([np.sin(m),np.cos(m)])).T
//         Api=prod(inv(prod(A.T,A)),A.T)#Api====A pseudo inverse
//         out=np.asarray(prod(Api,b))[0]
//         out=np.reshape(out,[2,len(out)//2])#First vector is the sin array second is the cos array
//         amplitudes=sum(out**2)**.5
//         phases=np.arctan2(*out)
//         return np.asarray([amplitudes,phases])#https://www.desmos.com/calculator/fnlwi71n9x
//
//     ha=harmonic_analysis
//     harmonics=100
//     nos=number_of_samples=harmonics*10#User Input++
//     cs=chunk_samples=[x(np.linspace(0,1,nos,False))for x in cf]
//     ca=[ha(x,harmonics)for x in cs]
//     di=display_grayscale_image
//     k=np.asarray([x[0]for x in ca])
//
//     #def poly2dcoeffs(X,Y,N):
//     #  out=[X*0+1]
//     #  for n in range(1,N+1):
//     #    for m in range(n):
//     #      out.append(X**m*Y**(n-m))
//     #return out
//
//     #HARM,NOTE=np.meshgrid(np.linspace(1, harmonics, harmonics), np.linspace(0, 132,132,False), copy=False)
//     #X,Y,Z=HARM,NOTE,k
//     #X = X.flatten()
//     #Y = Y.flatten()
//     #A=np.array(poly2dcoeffs(X,Y,100)).T
//     #B = Z.flatten()
//     #coeff, r, rank, s = np.linalg.lstsq(A, B)
//     #approx=np.sum([np.reshape(c*a,np.shape(k)) for c,a in zip(coeff,A.T)],0)
//     #di(k)
//     #di(approx)#This approximation is actually trash....
//
//     cp=[x[1][0]/tau for x in ca]#phases
//     cs2=[np.roll(cs[n],int(-len(cs[n])*cp[n]))for n in range(len(cs))]
//     cs3=[np.roll(x,-max_valued_index(x) )for x in cs]
//     return cs3
//
//
// savedir='/Users/Ryan/Desktop/Modulin Wavetable'
// wavetables=[]
// for I in range(nom):
//     wavetables.append(wavetable(megachunks[I]))
//     print("Calculated wavetable "+str(I)+" of "+str(nom-1))
// wavetables=np.array(wavetables)
// wavetables=full_range(wavetables)
// for I in range(nom):
//     save_image(wavetables[I],savedir+'/ModulinWavetable'+str(I))
//     print("Saved table "+str(I))
//
// # region pseudo_terminal definition
// from r import make_pseudo_terminal
// def pseudo_terminal(*_):pass # Easiest way to let PyCharm know that this is a valid def. The next line redefines it.
// exec(make_pseudo_terminal)
// pseudo_terminal()
// # endregion
