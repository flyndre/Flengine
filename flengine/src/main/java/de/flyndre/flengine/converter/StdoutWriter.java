package de.flyndre.flengine.converter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StdoutWriter {
    private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<>();

    public StdoutWriter(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        dispatchQueue.take().run();
                    }
                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
        }).start();
    }

    public void writeToStdout(String message) throws InterruptedException {
        dispatchQueue.put(new Runnable() {
            @Override
            public void run() {
                System.out.println(message);
            }
        });
    }
}
