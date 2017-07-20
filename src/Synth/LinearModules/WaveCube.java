package Synth.LinearModules;
import Common.r;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
public class WaveCube extends Oscillator
{
    public LinearModule xModInput;
    public double getSample(double ↈλ)
    {
        return waveCubeSample(ↈλ,inputFrequency.getSample(),xModInput.getSample());
    }
    //region For main modulin oscillator
    private double[][] transposeMatrix(double[][] m)
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
    private int[][] transposeMatrix(int[][] m)
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
    private double[][] loadWaveTable(String imagePath) throws IOException
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
    private double[][][] loadWaveCube(String pngPathPrefixWithoutNumberOrExtension,int numberOfImages) throws IOException
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
    private double lerpWaveCube(double x,double y,double z,double[][][] waveCube)
    {
        //z will loop, while x and y will be clamped.
        x=r.clamp(x,0,xl-1);
        y=r.clamp(y,0,yl-1);
        //
        final int cx=(int)r.ceil(x), cy=(int)r.ceil(y), cz=(int)r.mod((int)r.ceil(z),zl);//the %zl is here incase z==zl-.01 etc
        final int fx=(int)r.floor(x), fy=(int)r.floor(y), fz=(int)r.mod((int)r.floor(z),zl);
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
        return pxyz;//Must take into account the cyclic nature of the outputFileType and take into account what happens when the non-cyclic dimensions are out of bounds!
    }
    private double waveCubeSample(double λ,double f,double x)//f≣frequency, x≣the x-value in harmor. 0≤λ<1 ⋀ f>0 ⋀ 0≤x≤1. -1≤output≤1
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
    private double[][][] waveCube;
    private int xl, yl, zl;
    public WaveCube(String wavetablePrefix,int ↈwavetables,LinearModule xModInput)
    {
        this.xModInput=xModInput;
        try
        {
            System.out.println("Loading outputFileType cube "+wavetablePrefix+"...");
            waveCube=loadWaveCube(new File("").getAbsolutePath()+"/"+wavetablePrefix,ↈwavetables);
            System.out.println("Successfully loaded the outputFileType cube!");
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
            System.out.println("WARNING: Failed to load the outputFileType cube!");
        }
    }
    public static WaveCube modulinOscillator()
    {
        return new WaveCube("Modulin Wavetables/ModulinWavetable",21,new Constant(.5));
    }
    //endregion
}
