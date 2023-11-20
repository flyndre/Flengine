package de.flyndre.flengine.converter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StdoutWriter {

    public static synchronized void writeToStdout(String message){
        System.out.println(message);
    }
}
