public class ByteBlenderTest
{
    public static void main(String[] args)
    {
        byte a=(byte)13513590;
        byte b=(byte)12903123;
        System.out.println(a);
        System.out.println(b);
        System.out.println();
        int N=10;
        for(int i=0;i<=N;i++)
        {
            byte val=(byte)(((int)a*i+(int)b*(N-i))/N);
            System.out.println(val);
        }
    }
}
