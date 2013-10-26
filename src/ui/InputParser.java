package ui;

public final class InputParser {
    /**
     * Maximum allowed length of the input
     */
    private static final int            MAX_INPUT_LENGTH = 128 * 1024 - 1; // 128 KB
    /**
     * The array of valid commands signatures
     */
    private static final ValidCommand[] VALID_COMMANDS = {
        new ValidCommand("connect", 2),
        new ValidCommand("disconnect"),
        new ValidCommand("send", 1, true),
        new ValidCommand("logLevel", 1),
        new ValidCommand("help"),
        new ValidCommand("quit")
    };
    
    /**
     * Private constructor restricts instantiation of the class
     */
    private InputParser() {}
    
    private static void printHelpMessage() {
        System.out.println("TODO: Help text!");
    }
    
    /**
     * Parses a string provided by user and validates it against a set of 
     * known commands and their signatures.
     * @param input The input provided by a user.
     * @return If the input is valid returns an array of "String" objects, 
     *      otherwise return null. The first element of the array contains
     *      the command name, the other contains the arguments. 
     */
    public static String[] parseUserInput(String input) {
        // Check is the input string itself is valid
        if ((input == null) || (input.length() == 0)) {
            return null;
        } else if (input.length() >= MAX_INPUT_LENGTH) {
            System.out.println("Error! Input size is too big (max is 128KB).");
            return null;
        }
        
        // Split the input into tokens
        String          commandString = input.split("\\s+", 2)[0];
        ValidCommand    command = null;

        // Try to find the command
        for (ValidCommand validCommand : VALID_COMMANDS) {
            if (commandString.equalsIgnoreCase(validCommand.name)) {
                command = validCommand;
                break;
            }
        }
        
        // If command is not found print error message and return
        if (command == null) {
            System.out.println("Error! Command \"" + commandString + "\" is not"
                    + " recognized. Type \"help\" go get list of acceptable"
                    + " commands.");
            return null;
        }
        
        // If the command equals "help" then print help text and return nothing
        if (command.name.equals("help")) {
            InputParser.printHelpMessage();
            return null;
        }
        
        // Split the input into command and arguments
        String  tokens[] = input.split("\\s+", 1 + command.argCount);
        
        // Check the number of arguments
        if (tokens.length < (1 + command.argCount)) {
            System.out.println("Error! Too few arguments for the \"" + 
                    commandString + "\" command.");
            return null;
        }
        
        // Check if the last argument has multiple words
        if (tokens[tokens.length - 1].matches("\\S+\\s+.*") && 
                !command.greedyLastArg) {
            System.out.println("Error! Too many arguments for the \"" + 
                    commandString + "\" command.");
            return null;
        }
        
        return tokens;
    }
    
    /**
     * Private class representing a valid command and its signature.
     */
    private static class ValidCommand {
        public String name;
        public int argCount;
        public boolean greedyLastArg;

        ValidCommand(String name) {
            this(name, 0, false);
        }
        
        ValidCommand(String name, int argCount) {
            this(name, argCount, false);
        }
        
        ValidCommand(String name, int argCount, boolean greedyLastArg) {
            this.name = name;
            this.argCount = argCount;
            this.greedyLastArg = greedyLastArg;
        }
    }
}