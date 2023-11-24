package de.flyndre.flengine.logging.channel;

import de.flyndre.flengine.logging.ControllableFormatter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * An implementation of the {@code LogChannel} interface to log to a file.
 * @author David
 */
public class FileLogChannel implements LogChannel {

    private final Logger logger;
    private final String loggerFormat;
    private final String dateFormat;
    private final String filePrefix;
    private boolean isOpen = false;

    /**
     * Constructs a new instance of {@code FileLogChannel}.
     * @param logger the {@code Logger} on which the {@code Channel} should be attached to
     * @param loggerFormat the format to log in
     * @param dateFormat a format for a {@SimpleDateFormat} for naming the log files
     * @param filePrefix a prefix which will be prependet to the log file name
     */
    public FileLogChannel(Logger logger, String loggerFormat, String dateFormat, String filePrefix) {
        this.logger = logger;
        this.loggerFormat = loggerFormat;
        this.dateFormat = dateFormat;
        this.filePrefix = filePrefix;
    }

    public void setOpen(boolean open) {
        if (isOpen == open) return;
        if (open) {
            open();
        } else {
            close();
        }
    }

    private void open() {
        var timestamp = new SimpleDateFormat(dateFormat).format(new Date());
        try {
            var handler = new FileHandler(new File(filePrefix + timestamp + ".log").getAbsolutePath());
            handler.setFormatter(new ControllableFormatter(loggerFormat));
            logger.addHandler(handler);
            isOpen = true;
        } catch (IOException e) {
            //
        }
    }

    private void close() {
        for (Handler h : logger.getHandlers()) {
            if (h instanceof FileHandler)
                logger.removeHandler(h);
        }
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
