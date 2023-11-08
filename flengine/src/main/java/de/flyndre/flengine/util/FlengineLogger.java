package de.flyndre.flengine.util;

import de.flyndre.flengine.Flengine;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FlengineLogger extends Logger{

    private final static String pathToFile = "flengine\\src\\main\\java\\de\\flyndre\\flengine\\log.log";

    public static Logger getLogger(String name) {

        Logger logger = Logger.getLogger(name);

        try {
            FileHandler fh = new FileHandler(pathToFile);
            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            return logger;

        }catch (Exception e){
            return Logger.getLogger("DefaultLogger");
        }
    }
    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     */
    protected FlengineLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }




}
