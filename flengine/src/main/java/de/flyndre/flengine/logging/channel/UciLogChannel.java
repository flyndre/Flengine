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
        isOpen = open;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
