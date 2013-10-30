package controller;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Custom wrapper for the log4j Logger object. Is responsible for the 
 * initialization and log level changes of the log4j Logger.
 * @author Danila Klimenko
 */
public final class EchoLogger {
    /**
     * Log record pattern for the log4j Logger object
     */
    private static final String LOG_PATTERN = "%d{ISO8601} %-5p [%t] %c: %m%n";
    /**
     * Path to the log file
     */
    private static final String LOG_PATH = "logs/client.log";
    /**
     * log4j Logger object
     */
    private static final Logger logger = Logger.getLogger("EchoClient");
    
    /**
     * Initializes the internal log4j logger object
     * @throws IOException Thrown in case the log file cannot be accessed
     */
    public static void setupLogger() throws IOException {
        // Set default level
        logger.setLevel(Level.INFO);
        
        // Make sure the directories exist
        File file = new File(LOG_PATH);
        file.getParentFile().mkdirs();
        
        // Setup file appender
        PatternLayout layout = new PatternLayout(LOG_PATTERN);
        FileAppender fa = new FileAppender(layout, LOG_PATH, true);
        logger.addAppender(fa);
    }
    
    //<editor-fold defaultstate="collapsed" desc="log4j.Logger call wrappers">
    public static void debug(Object message) {
        logger.debug(message);
    }
    public static void info(Object message) {
        logger.info(message);
    }
    public static void warn(Object message) {
        logger.warn(message);
    }
    public static void error(Object message) {
        logger.error(message);
    }
    public static void fatal(Object message) {
        logger.fatal(message);
    }
//</editor-fold>
    
    /**
     * Changes logging level
     * @param levelString New logging level name.
     * @return Info string
     */
    public static String setLogLevel(String levelString) {
        String resultString = null;
        Level level = null;
        
        if (levelString.equalsIgnoreCase("all")) {
            level = Level.ALL;
        } else if (levelString.equalsIgnoreCase("debug")) {
            level = Level.DEBUG;
        } else if (levelString.equalsIgnoreCase("info")) {
            level = Level.INFO;
        } else if (levelString.equalsIgnoreCase("warn")) {
            level = Level.WARN;
        } else if (levelString.equalsIgnoreCase("error")) {
            level = Level.ERROR;
        } else if (levelString.equalsIgnoreCase("fatal")) {
            level = Level.FATAL;
        } else if (levelString.equalsIgnoreCase("off")) {
            level = Level.OFF;
        } else {
            resultString = "Error! Logging level \"" + levelString 
                    + "\" is n ot valid.";
        }
        
        if (level != null) {
            logger.setLevel(level);
            resultString = "Logging level changed to \"" + levelString + "\".";
        }
        
        return resultString;
    }
    
    /**
     * Private constructor restricts instantiation of the class
     */
    private EchoLogger() {}
}