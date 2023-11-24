package de.flyndre.flengine.logging.channel;

import de.flyndre.flengine.logging.ControllableFormatter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class FileLogChannel implements LogChannel {

    private final String loggerName;
    private final String loggerFormat;
    private final String dateFormat;
    private final String filePrefix;
    private boolean isOpen = false;

    public FileLogChannel(String loggerName, String loggerFormat, String dateFormat, String filePrefix) {
        this.loggerName = loggerName;
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
            Logger.getLogger(loggerName).addHandler(handler);
            isOpen = true;
        } catch (IOException e) {
            //
        }
    }

    private void close() {
        var logger = Logger.getLogger(loggerName);
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
