import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
@SuppressWarnings("SuspiciousNameCombination")
public class WaveCube
{
    public static double[][] transposeMatrix(double[][] m)
    {
        double[][] temp=new double[m[0].length][m.length];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
            {
                temp[j][i]=m[i][j];
            }
        }
        return temp;
    }
    public static int[][] transposeMatrix(int[][] m)
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
    public static double[][][] loadWaveCube(String pngPathPrefixWithoutNumberOrExtension,int numberOfImages) throws IOException
    {
        double[][] initTable=loadWaveTable(pngPathPrefixWithoutNumberOrExtension+0+".png");
        double[][][] out=new double[numberOfImages][initTable[0].length][initTable.length];
        for(int i=0;i<numberOfImages;i++)
        {
            out[i]=loadWaveTable(pngPathPrefixWithoutNumberOrExtension+i+".png");
        }
        return out;
    }
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static double lerpWaveCube(double x,double y,double z,double[][][]waveCube)
    {
        //z will loop, while x and y will be clamped.
        x=rOutpost.clamp(x,0,xl-1);
        y=rOutpost.clamp(y,0,yl-1);
        //
        final int cx=(int)r.ceil(x),cy=(int)r.ceil(y),cz=((int)r.ceil(z))%zl;//the %zl is here incase z==zl-.01 etc
        final int fx=(int)r.floor(x),fy=(int)r.floor(y),fz=((int)r.floor(z))%zl;
        //
        final double p000=waveCube[fx][fy][fz];
        final double p001=waveCube[fx][fy][cz];
        final double p010=waveCube[fx][cy][fz];
        final double p011=waveCube[fx][cy][cz];
        final double p100=waveCube[cx][fy][fz];
        final double p101=waveCube[cx][fy][cz];
        final double p110=waveCube[cx][cy][fz];
        final double p111=waveCube[cx][cy][cz];
        //
        final double x1α=x-fx;//≣1-x0α
        final double x0α=1-x1α;//≣x-fx ⟵ Setting it to this caused problems: when cx==fx ∴ x==fx&&x==cx ∴ x-fx==0&&x-cx==0
        final double px00=p100*x1α+p000*x0α;
        final double px01=p101*x1α+p001*x0α;
        final double px10=p110*x1α+p010*x0α;
        final double px11=p111*x1α+p011*x0α;
        //
        final double y1α=y-fy;//≣1-y0α
        final double y0α=1-y1α;//≣y-fy ⟵ Setting it to this caused problems: when cy==fy ∴ y==fy&&y==cy ∴ y-fy==0&&y-cy==0
        final double pxy0=px10*y1α+px00*y0α;
        final double pxy1=px11*y1α+px01*y0α;
        //
        final double z1α=z-fz;//≣1-z0α
        final double z0α=1-z1α;//≣z-fz ⟵ Setting it to this caused problems: when cz==fz ∴ z==fz&&z==cz ∴ z-fz==0&&z-cz==0
        final double pxyz=pxy1*z1α+pxy0*z0α;
        //
        return pxyz;//Must take into account the cyclic nature of the wave and take into account what happens when the non-cyclic dimensions are out of bounds!
    }
    public static double waveCubeSample(double λ,double f,double x)//f≣frequency, x≣the x-value in harmor. 0≤λ<1 ⋀ f>0 ⋀ 0≤x≤1. -1≤output≤1
    {
        double z=λ%1*zl;
        //
        //We calculate the note number from the frequency: https://www.desmos.com/calculator/beotlb0xow
        //This is based on the fact that FL Studio has a set number of notes and the conversion algorithm is in the python code. This is it's inverse.
        double y=17.312340490667560888319096172022705649*r.ln(f)-48.376316562295915248836189714582197614087;
        x*=xl;
        return lerpWaveCube(x,y,z,waveCube);
    }
    // public double sample(double vibratoAmp,double vibratoFreq,double envelopeLevel,double volume,)
    public static double[][][]waveCube;
    public static int xl,yl,zl;
    static
    {
        try
        {
            System.out.println("Loaded wave cube...");
            waveCube=loadWaveCube(new File("").getAbsolutePath()+"/"+"Modulin Wavetables/ModulinWavetable",21);
            System.out.println("Successfully loaded the wave cube!");
            xl=waveCube.length;
            yl=waveCube[0].length;
            zl=waveCube[0][0].length;
            System.out.println("zl="+zl);
            System.out.println("xl="+xl);
            System.out.println("yl="+yl);


        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("WARNING: Failed to load the wave cube!");
        }
    }
    public static void main(String[] args) throws IOException
    {
        // Color[][] colors=loadPixelsFromImage(new File("/Users/Ryan/Desktop/scrunge.png.png"));
        // System.out.println("Color[0][0] = "+colors[0][0].getRGBComponents(null)[0]);
        System.out.println(java.util.Arrays.deepToString(loadWaveTable("/Users/Ryan/Desktop/scrunge.png.png")));
        // System.out.println(java.util.Arrays.deepToString(doubles));
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
