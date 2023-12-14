package de.flyndre.flengine.converter;

/**
 * Provides a thread safe possibility to write to stdout.
 * @author Paul
 */
public class StdoutWriter {

    /**
     * Prints the given string to the standard output.
     * @param message the message to be printed to stdout
     */
    public static synchronized void writeToStdout(String message){
        System.out.println(message);
    }
}
