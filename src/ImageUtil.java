import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
public class ImageUtil
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
        double[][][] out=new double[initTable.length][initTable[0].length][numberOfImages];
        for(int i=0;i<numberOfImages;i++)
        {
            out[i]=loadWaveTable(pngPathPrefixWithoutNumberOrExtension+i+".png");
        }
        return out;
    }
    public double lerpWaveCube(double x,double y,double z,double[][][]waveCube)
    {
        return 0;//Must take into account the cyclic nature of the wave and take into account what happens when the non-cyclic dimensions are out of bounds!
    }
    public double waveCubeSample(long sampleNumber,long sampleRate,double frequency,double XValue)
    {
        return 0;
    }
    // public double sample(double vibratoAmp,double vibratoFreq,double envelopeLevel,double volume,)
    public static void main(String[] args) throws IOException
    {
        // Color[][] colors=loadPixelsFromImage(new File("/Users/Ryan/Desktop/scrunge.png.png"));
        // System.out.println("Color[0][0] = "+colors[0][0].getRGBComponents(null)[0]);
        System.out.println(java.util.Arrays.deepToString(loadWaveTable("/Users/Ryan/Desktop/scrunge.png.png")));
        double[][][] doubles=loadWaveCube("/Users/Ryan/Desktop/Modulin Wavetable/ModulinWavetable",21);
        System.out.println("LOADED WAVE CUBE!");
        // System.out.println(java.util.Arrays.deepToString(doubles));
    }
}