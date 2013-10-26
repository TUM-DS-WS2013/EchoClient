package controller;

import CustomExceptions.ServerConnectionException;
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
    
    public String ProcessMessages(String[] inputMessage)
    {
        String output = new String();
        if (inputMessage.length < 1 || objSocketClient == null)
            return output;
        try
        {
            if (inputMessage[0].compareToIgnoreCase("connect") == 0)
            {
                output = objSocketClient.Connect(inputMessage[1], Integer.parseInt(inputMessage[2]));
            }
            else if (inputMessage[0].compareToIgnoreCase("disconnect") == 0)
            {
                objSocketClient.DisConnect();
                output = "Connection terminated: "+ objSocketClient.GetServerIP() + 
                        " /"+ Integer.toString(objSocketClient.GetServerPort());
            }
            else if (inputMessage[0].compareToIgnoreCase("send") == 0)
            {
                output = objSocketClient.SendRecvMessage(inputMessage[1]);
            }
            else if (inputMessage[0].compareToIgnoreCase("logLevel") == 0)
            {

            }
            else if (inputMessage[0].compareToIgnoreCase("quit") == 0)
            {
                objSocketClient.DisConnect();
                output = "Application exit!";
            }
        }
        catch (ServerConnectionException serverNotConnectedEx)
        {
            output = serverNotConnectedEx.GetErrorMsg();
        }
        catch (UnknownHostException hostException)
        {
            output = "Error! See the log file for details";
        }
        catch(IOException ioexception)
        {
            output = "Error! See the log file for details";
        }

        return output;
    }
}