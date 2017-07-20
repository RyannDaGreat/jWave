package Arduino;
import Common.r;
import Synth.SynthTest;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
public class BasicLEDExample
{
    public static doubleAccepter[] functions=new doubleAccepter[128];
    public interface doubleAccepter
    {
        void f(double x);
    }
    static
    {
        // try
        // {
        //     // activate();
        // }
        // catch(IOException e)
        // {
        //     System.out.println("FAILED TO ACTIVATE ARDUINO!");
        //     e.printStackTrace();
        // }
    }
    public static void flush(String s)
    {
        // System.out.print("JJJJ");
        // System.out.println(s.length());
        // System.out.print(s.charAt(0));
        // System.out.print(s.charAt(1));
        // System.out.print(s.charAt(2));
        // System.out.print(s.charAt(3));
        // System.out.print(s.charAt(4));
        for(char c : s.toCharArray())
        {
            // System.out.print("LL"+c);
            try
            {
                double x=Double.parseDouble(s);
                System.out.println(x);
                double x1=x/1024*48-24;
                SynthTest.c.value=x1;
                // System.out.println(x1);
            }
            catch(Exception ignored)
            {
            }
        }
        // System.out.println("HUHU");
    }
    public static void activate()
    {
        // functions[0]=
        // functions[1]=
        // functions[2]=
        // functions[3]=
        // functions[4]=
        // functions[5]=
        // functions[6]=
        // functions[7]=
        // functions[8]=System.out::println;
        Arduino arduino=new Arduino("cu.wchusbserial1420",9600); //enter the port name here, and ensure that Arduino.Arduino is connected, otherwise exception will be thrown.
        arduino.openConnection();
        LinkedList<Integer> s=new LinkedList<>();
        //noinspection InfiniteLoopStatement
        // r.startTimerThread(()->arduino.serialWrite('\n'),.1);
        new Thread(()->
                   {
                       String S="";
                       while(true)
                       {
                           // r.delay(.1);
                           int temp1=0;
                           try
                           {
                               temp1=arduino.comPort.getInputStream().available();
                           }
                           catch(IOException e)
                           {
                               e.printStackTrace();
                           }
                           byte[] temp2=new byte[temp1];
                           arduino.comPort.readBytes(temp2,temp1);
                           for(byte temp3 : temp2)
                           {
                               if((char)temp3=='\n')
                               {
                                   flush(S);
                                   S="";
                               }
                               else
                               {
                                   S+=(char)temp3;
                               }
                               // Integer b=(int)temp3-Byte.MIN_VALUE;//Unsigned Byte
                               // System.out.println(b);
                               // if(b<128)
                               // {
                               //     s.push(b);
                               // }
                               // else
                               // {
                               //     double x=0,v=1;
                               //     while(!s.isEmpty())
                               //     {
                               //         x+=s.pop()/(v*=128);
                               //     }
                               //     int i=b-128;
                               //     if(functions[i]==null)
                               //     {
                               //         System.out.println("ARDUINO WARNING: Tried to access function["+i+"] == null; x == "+x);
                               //     }
                               //     else
                               //     {
                               //         functions[i].f(x);
                               //     }
                               // }
                           }
                       }
                   }).start();
    }
}