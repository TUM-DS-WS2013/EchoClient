package ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.net.Socket;

//import org.apache.log4j.Logger;

public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try {
            Socket client = new Socket("131.159.52.1", 50000);
            System.out.println("Just connected to " +
                               client.getRemoteSocketAddress());

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream s_out = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            BufferedReader s_in = 
                    new BufferedReader(new InputStreamReader(inFromServer));
            
            // Read the weclome message
            s_in.readLine();
            s_in.readLine();
            
            while (true) {
                System.out.print("echo> ");
                userInput = in.readLine();
                if (userInput.equalsIgnoreCase("quit"))
                    break;
                
                s_out.write(userInput.getBytes());
                s_out.write(0x0D);
                s_out.flush();
                System.out.println("Server says: " + s_in.readLine());
                s_in.readLine();
            }
            
            s_out.close();
            s_in.close();
            client.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
