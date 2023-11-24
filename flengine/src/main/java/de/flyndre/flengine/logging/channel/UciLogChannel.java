package de.flyndre.flengine.logging.channel;

import de.flyndre.flengine.logging.ControllableFormatter;
import de.flyndre.flengine.logging.UciHandler;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class UciLogChannel implements LogChannel {

    private final String loggerName;
    private final String loggerFormat;
    private boolean isOpen = false;

    public UciLogChannel(String loggerName, String loggerFormat) {
        this.loggerName = loggerName;
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
        var handler = new UciHandler();
        handler.setFormatter(new ControllableFormatter(loggerFormat));
        Logger.getLogger(loggerName).addHandler(handler);
        isOpen = true;
    }

    private void close() {
        var logger = Logger.getLogger(loggerName);
        for (Handler h : logger.getHandlers()) {
            if (h instanceof UciHandler)
                logger.removeHandler(h);
        }
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
