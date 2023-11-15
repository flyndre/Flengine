package de.flyndre.flengine.util;

import de.flyndre.flengine.converter.RequestHandler;

import java.util.logging.ConsoleHandler;
import java.util.logging.ErrorManager;
import java.util.logging.LogRecord;

/**
 * A custom {@code Handler} for logging that publishes in UCI compatible format.
 * @author David
 */
public class UciHandler extends ConsoleHandler {
    @Override
    public void publish(LogRecord record) {
        if (!super.isLoggable(record)) {
            return;
        }
        String msg;
        try {
            msg = super.getFormatter().format(record);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            super.reportError(null, ex, ErrorManager.FORMAT_FAILURE);
            return;
        }

        try {
            RequestHandler.printStdout(msg);
        } catch (Exception ex) {
            // We don't want to throw an exception here, but we
            // report the exception to any registered ErrorManager.
            super.reportError(null, ex, ErrorManager.WRITE_FAILURE);
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
