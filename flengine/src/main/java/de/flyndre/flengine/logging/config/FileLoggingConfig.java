package de.flyndre.flengine.logging.config;

import de.flyndre.flengine.logging.ControllableFormatter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class FileLoggingConfig implements LoggingConfig {

    private final String loggerName;
    private final String loggerFormat;
    private final String dateFormat;
    private final String filePrefix;
    private boolean isActive = false;

    public FileLoggingConfig(String loggerName, String loggerFormat, String dateFormat, String filePrefix) {
        this.loggerName = loggerName;
        this.loggerFormat = loggerFormat;
        this.dateFormat = dateFormat;
        this.filePrefix = filePrefix;
    }

    public void setActive(boolean active) {
        if (isActive == active) return;
        if (active) {
            activate();
        } else {
            deactivate();
        }
    }

    private void activate() {
        var timestamp = new SimpleDateFormat(dateFormat).format(new Date());
        try {
            var handler = new FileHandler(new File(filePrefix + timestamp + ".log").getAbsolutePath());
            handler.setFormatter(new ControllableFormatter(loggerFormat));
            Logger.getLogger(loggerName).addHandler(handler);
            isActive = true;
        } catch (IOException e) {
            //
        }
    }

    private void deactivate() {
        var logger = Logger.getLogger(loggerName);
        for (Handler h : logger.getHandlers()) {
            if (h instanceof FileHandler)
                logger.removeHandler(h);
        }
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }
}
