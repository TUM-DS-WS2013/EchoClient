package ui;

import controller.EchoController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

//import org.apache.log4j.Logger;

public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        try
        {
            EchoController controller = new EchoController();
            while (true) {
                System.out.print("EchoClient> ");
                userInput = in.readLine();
                String[] strInputTokens = InputParser.parseUserInput(userInput);
                if (strInputTokens == null)
                    continue;
                String outPut = controller.ProcessMessages(strInputTokens);
                System.out.print("EchoClient> " + outPut + "\n");
                if (strInputTokens[0].equalsIgnoreCase("quit"))
                    break;
            }
        }
        catch(IOException exception)
        {
        }
    }

}
