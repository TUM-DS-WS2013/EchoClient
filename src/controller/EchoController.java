package controller;

import communication.EchoSocketClient;
import java.io.IOException;
import java.net.UnknownHostException;

public class EchoController
{
    private final EchoSocketClient objSocketClient;
    
    public EchoController()
    {
        objSocketClient = new EchoSocketClient();
    }
    
    public void ProcessMessage(String[] inputMessage)
    {
        if (inputMessage.length < 1 || objSocketClient == null)
            return;
        try
        {
            if (inputMessage[0].compareToIgnoreCase("connect") == 0)
            {
                objSocketClient.Connect(inputMessage[1], Integer.parseInt(inputMessage[2]));
            }
            else if (inputMessage[0].compareToIgnoreCase("disconnect") == 0)
            {
                objSocketClient.DisConnect();
            }
            else if (inputMessage[0].compareToIgnoreCase("send") == 0)
            {
                objSocketClient.SendRecvMessage(inputMessage[1]);
            }
            else if (inputMessage[0].compareToIgnoreCase("logLevel") == 0)
            {

            }
            else if (inputMessage[0].compareToIgnoreCase("quit") == 0)
            {
                objSocketClient.DisConnect();
            }
        }
        catch (UnknownHostException hostException)
        {
            
        }
        catch(IOException ioexception)
        {
            
        }
    }
}