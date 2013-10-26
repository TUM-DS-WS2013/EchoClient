package CustomExceptions;

public class ServerConnectionException extends Exception{
    
     //Parameterless Constructor
      public ServerConnectionException() {}

      //Constructor that accepts a message
      public ServerConnectionException(String message)
      {
         super(message);
      }
      
      public String GetErrorMsg() { return super.getMessage(); }
}
