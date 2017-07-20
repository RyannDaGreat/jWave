package Arduino;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.Arrays;
public class BasicLEDExample
{
    public static void main(String[] args) throws IOException
    {
        System.out.println(Arrays.toString(SerialPort.getCommPorts()));
        for(SerialPort serialPort : SerialPort.getCommPorts())
        {
            System.out.println(serialPort.getDescriptivePortName());
            // System.out.println(serialPort.());
        }
        // SerialPort.getCommPorts()[0].getDescriptivePortName()
        Arduino arduino=new Arduino("cu.wchusbserial1420",9600); //enter the port name here, and ensure that Arduino.Arduino is connected, otherwise exception will be thrown.
        arduino.openConnection();
        System.out.println("Enter 1 to switch LED on and 0  to switch LED off");
        // char input=ob.nextLine().charAt(0);
        while(true)
        {
            // System.out.println("kasf");
            int a=arduino.comPort.getInputStream().available();
            byte[] b=new byte[a];
            arduino.comPort.readBytes(b,a);
            for(byte c : b)
            {
                System.out.print((char)c);
            }
            // System.out.println();
        }
        //     arduino.serialWrite(input);
        // arduino.serialRead();
        // ob.close();
        // arduino.closeConnection();
    }
}
