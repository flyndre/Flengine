package de.flyndre.flengine.converter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Provides a threadsafe possibility to write to stdout
 */
public class StdoutWriter {

    /**
     * Prints the given string to the standard output
     * @param message the message to be printed to stdout
     */
    public static synchronized void writeToStdout(String message){
        System.out.println(message);
    }
}
