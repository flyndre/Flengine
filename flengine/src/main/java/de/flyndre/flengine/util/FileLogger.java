package de.flyndre.flengine.util;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger extends Logger{

    private final static File logfile = new File(".\\log.log");

    private static FileHandler fileHandler;

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
    protected FileLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static Logger getLogger(String name) {

        Logger logger = Logger.getLogger(name);

        try {
            if (fileHandler == null) {
                removeLockFiles(logfile);
                fileHandler = new FileHandler(logfile.getAbsolutePath(), true);
                fileHandler.setFormatter(new SimpleFormatter());
            }
            logger.addHandler(fileHandler);
            return logger;

        } catch (Exception e) {
            return Logger.getLogger(name);
        }
    }

    private static void removeLockFiles(File logfile) {
        var lockFileRegex = ".lck";
        var dir = logfile.getParentFile();
        var files = dir.listFiles();
        if (files == null) return;
        for (var file : files) {
            if (file.getName().contains(lockFileRegex)) file.delete();
        }
    }
}
