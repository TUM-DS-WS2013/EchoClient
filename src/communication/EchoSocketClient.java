package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoSocketClient
{
    private Socket objSocketClient;
    private InputStream objSocketInputStream;
    private OutputStream objSocketOutPutStream;
    
    public EchoSocketClient()
    {
        objSocketClient = null;
        objSocketInputStream = null;
        objSocketOutPutStream = null;
    }
   
    public void Connect(String strIP, int nPortNumber) throws UnknownHostException,
              IOException
    {
        if (objSocketClient == null && !strIP.isEmpty())
        {
            objSocketClient = new Socket(strIP, nPortNumber);
            objSocketInputStream = objSocketClient.getInputStream();
            objSocketOutPutStream = objSocketClient.getOutputStream();
        }
    }
    
    public void DisConnect() throws IOException
    {
        if (objSocketInputStream != null)
            objSocketInputStream.close();
        if (objSocketOutPutStream != null)
            objSocketOutPutStream.close();
        if (objSocketClient != null)
            objSocketClient.close();
        
        objSocketInputStream = null;
        objSocketOutPutStream = null;
        objSocketClient = null;
    }
    
    public void SendMessage(String strMessage)throws IOException
    {
        if (!strMessage.isEmpty()&& objSocketOutPutStream != null)
        {
            byte[] msgBytes = strMessage.getBytes();
            objSocketOutPutStream.write(msgBytes);
            objSocketOutPutStream.flush();
        }
    }
    
    public String RecieveMessage() throws IOException
    {
        String strRecvMsg = new String();
        if (objSocketInputStream != null && objSocketInputStream.available() > 0)
        {
            byte[] recieveMsgBytes = new byte[objSocketInputStream.available()];
            int readBytes = objSocketInputStream.read(recieveMsgBytes);
            if (readBytes != 0 && readBytes != -1)
                strRecvMsg = recieveMsgBytes.toString();
        }
        
        return strRecvMsg;
    }
    
    public String SendRecvMessage(String strMessage) throws IOException
    {
        SendMessage(strMessage);
        return RecieveMessage();
    }
}