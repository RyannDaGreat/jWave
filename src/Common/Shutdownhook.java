package Common;//Created by Ryan on 7/20/17.
public class Shutdownhook
{
    public static void main(String[] args)
    {
        new Thread(()->Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutdown");
            }
        })).start();
        while(true)
        {
            r.delay(.1);
            System.out.println(r.toc());
        }
    }
}
