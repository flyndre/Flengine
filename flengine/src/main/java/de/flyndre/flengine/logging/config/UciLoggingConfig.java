package de.flyndre.flengine.logging.config;

import de.flyndre.flengine.logging.ControllableFormatter;
import de.flyndre.flengine.logging.UciHandler;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class UciLoggingConfig implements LoggingConfig {

    private final String loggerName;
    private final String loggerFormat;
    private boolean isActive = false;

    public UciLoggingConfig(String loggerName, String loggerFormat) {
        this.loggerName = loggerName;
        this.loggerFormat = loggerFormat;
    }

    public void setActive(boolean active) {
        if (isActive == active) return;
        if (active) {
            var handler = new UciHandler();
            handler.setFormatter(new ControllableFormatter(loggerFormat));
            Logger.getLogger(loggerName).addHandler(handler);
        } else {
            var logger = Logger.getLogger(loggerName);
            for (Handler h : logger.getHandlers()) {
                if (h instanceof UciHandler)
                    logger.removeHandler(h);
            }
        }
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }
}
