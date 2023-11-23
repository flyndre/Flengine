package de.flyndre.flengine.logging.config;

import de.flyndre.flengine.logging.ControllableFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ConsoleLoggingConfig implements LoggingConfig {

    private final String loggerName;
    private final String loggerFormat;
    private boolean isActive = false;

    public ConsoleLoggingConfig(String loggerName, String loggerFormat) {
        this.loggerName = loggerName;
        this.loggerFormat = loggerFormat;
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
        var handler = new ConsoleHandler();
        handler.setFormatter(new ControllableFormatter(loggerFormat));
        Logger.getLogger(loggerName).addHandler(handler);
        isActive = true;
    }

    private void deactivate() {
        var logger = Logger.getLogger(loggerName);
        for (Handler h : logger.getHandlers()) {
            if (h instanceof ConsoleHandler)
                logger.removeHandler(h);
        }
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }
}
