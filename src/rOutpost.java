@SuppressWarnings({"WeakerAccess","FinalStaticMethod"})
public class rOutpost//To be merged with the r class in the future...
{
    public static void main(String[] as)
    {
        System.out.println(audioRangeToInt(1));
    }
    public final static double clamp(double x,double min,double max)
    {
        assert min<=max;
        return x<min?min:x>max?max:x;
    }
    public final static double lerp(double x,double xmin,double xmax,double ymin,double ymax)
    {
        assert xmin<=xmax;
        assert ymin<=ymax;
        x-=xmin;//∴ x>=0 (If x was min before, now x is 0)
        x/=xmax-xmin;//Divide x by its range
        x*=ymax-ymin;//Multiply x by int's range
        x+=ymin;
        return x;
    }
    public final static double clampedLerp(double x,double xmin,double xmax,double ymin,double ymax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2³¹，2³¹﹣1］
    {
        assert xmin<=xmax;
        assert ymin<=ymax;
        return clamp(lerp(x,xmin,xmax,ymin,ymax),ymin,ymax);
    }
    public final static int doubleRangeToInt(double x,double xmin,double xmax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2³¹，2³¹﹣1］
    {
        assert xmin<=xmax;
        int imin=-2147483648, imax=2147483647;//Ints are 32-bit signed integers. 2³¹﹦2147483648
        return (int)clampedLerp(x,xmin,xmax,imin,imax);
    }
    public final static short doubleRangeToShort(double x,double xmin,double xmax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2¹⁵，2¹⁵﹣1］
    {
        assert xmin<=xmax;
        short smin=-32768, smax=32767;//Shorts are 16-bit signed integers. 2¹⁵﹦32768
        return (short)clampedLerp(x,xmin,xmax,smin,smax);
    }
    public final static int audioRangeToShort(double x)//x should ∈［﹣1，1］
    {
        return doubleRangeToInt(x,-1,1);
    }
    public final static short audioRangeToInt(double x)//x should ∈［﹣1，1］
    {
        return doubleRangeToShort(x,-1,1);
    }
}
