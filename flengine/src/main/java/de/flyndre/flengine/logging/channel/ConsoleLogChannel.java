package de.flyndre.flengine.logging.channel;

import de.flyndre.flengine.logging.ControllableFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * An implementation of the {@code LogChannel} interface to log to the console.
 * @author David
 */
public class ConsoleLogChannel implements LogChannel {

    private final Logger logger;
    private final String loggerFormat;
    private boolean isOpen = false;

    /**
     * Constructs a new instance of {@code ConsoleLogChannel}.
     * @param logger the {@code Logger} on which the {@code Channel} should be attached to
     * @param loggerFormat the format to log in
     */
    public ConsoleLogChannel(Logger logger, String loggerFormat) {
        this.logger = logger;
        this.loggerFormat = loggerFormat;
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
        var handler = new ConsoleHandler();
        handler.setFormatter(new ControllableFormatter(loggerFormat));
        logger.addHandler(handler);
        isOpen = true;
    }

    private void close() {
        for (Handler h : logger.getHandlers()) {
            if (h instanceof ConsoleHandler)
                logger.removeHandler(h);
        }
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
