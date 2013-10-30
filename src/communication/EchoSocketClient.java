package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import CustomExceptions.ServerConnectionException;

/**
 * This class is responsible for communication via Sockets.
 */
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
   
    /**
     * Get the IP address of server.
     * @return the IP address of server to whom socket is connected.
     */
    public String GetServerIP() 
    { 
        String strServerIP = new String();
        if (objSocketClient != null)
        {
            InetAddress address;
            address = objSocketClient.getInetAddress();
            strServerIP = address.getHostAddress();
        }
        return strServerIP;
    }
    
    /**
     * Get the port number of server.
     * @return port number to whom the socket is connected.
     */
    public int GetServerPort()
    { 
        int nServerPort = 0;
        if (objSocketClient != null)
        {
           nServerPort = objSocketClient.getPort();
        }
        return nServerPort; 
    }
    
    /**
     * Try to establish a connection with server.
     * @param strIP IP address of server.
     * @param nPortNumber port number of the server.
     * @return information string.
     * @throws UnknownHostException
     * @throws IOException
     * @throws ServerConnectionException 
     */
    public String Connect(String strIP, int nPortNumber) throws UnknownHostException,
              IOException, ServerConnectionException
    {
        String outputMsg;
        if (objSocketClient == null && !strIP.isEmpty())
        {
            objSocketClient = new Socket(strIP, nPortNumber);
            objSocketInputStream = objSocketClient.getInputStream();
            objSocketOutPutStream = objSocketClient.getOutputStream();
            outputMsg = RecieveMessage();
        }
        else
            outputMsg = "Server already connected!";
        return outputMsg;
    }
    
    /**
     * Disconnect from the server to whom socket is connected.
     * @throws IOException 
     */
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
    
    /**
     * Send the message to the server to whom socket is connected.
     * @param strMessage Message which has to be send to server.
     * @throws IOException
     * @throws ServerConnectionException 
     */
    public void SendMessage(String strMessage) throws IOException, ServerConnectionException
    {
        if (objSocketOutPutStream != null)
        {
            byte[] msgBytes = strMessage.getBytes();
            objSocketOutPutStream.write(msgBytes);
            objSocketOutPutStream.write(0x0D);
            objSocketOutPutStream.flush();
        }
        else 
            throw new ServerConnectionException("Error! Not connected!");
    }
    
    /**
     * Receive the message from the server to whom socket is connected.
     * @return Read message from the server.
     * @throws IOException
     * @throws ServerConnectionException 
     */
    public String RecieveMessage() throws IOException, ServerConnectionException
    {
        String strRecvMsg = new String();
        
        if (objSocketInputStream != null)
        {
            byte []recieveMsgBytes;
            byte[] readBuf1 = new byte[1];
            int readBytes = objSocketInputStream.read(readBuf1);
            if (objSocketInputStream.available() > 0)
            {
                byte[] readBuf2 = new byte[objSocketInputStream.available()];
                readBytes = objSocketInputStream.read(readBuf2);
                recieveMsgBytes = new byte[readBuf2.length + readBuf1.length];
                System.arraycopy(readBuf1, 0, recieveMsgBytes, 0, 1);
                System.arraycopy(readBuf2, 0, recieveMsgBytes, 1, readBuf2.length);
            }
            else
            {
                recieveMsgBytes = new byte[1];
                recieveMsgBytes[0] = readBuf1[0];
            }
            
            if (readBytes != 0 && readBytes != -1)
            {
                strRecvMsg = new String(recieveMsgBytes, 0, recieveMsgBytes.length-2);
            }
        }
        else 
            throw new ServerConnectionException("Error! Not connected!");
        
        return strRecvMsg;
    }
    
    /**
     * Send and receive the message to/from the server respectively.
     * @param strMessage Message which has to be send to server.
     * @return Read message from the server.
     * @throws IOException
     * @throws ServerConnectionException 
     */
    public String SendRecvMessage(String strMessage) throws IOException, ServerConnectionException
    {
        SendMessage(strMessage);
        return RecieveMessage();
    }
}