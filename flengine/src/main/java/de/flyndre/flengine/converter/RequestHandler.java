package de.flyndre.flengine.converter;

import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestHandler {
    private String engineName = "Flengine";
    private String engineAuthor = "TeamFlyndre";
    private String position = "";
    private String[] moves = {};
    private Scanner systemInScanner;
    private Organizer organizer;
    //attributes for synchronising the print of the calculated move with the gui's stop command
    private AtomicBoolean isStopped = new AtomicBoolean(false);
    private AtomicBoolean isCalculated = new AtomicBoolean(false);
    private String calculatedMove;

    /**
     * Startup for the chess engine
     */
    public void startUp(){
        systemInScanner = new Scanner(System.in);
        String input = "";
        boolean isRunning = true;

        while(isRunning){
            input = systemInScanner.nextLine();

            String[] splittedInput = input.split(" ");

            while(true){
                switch(splittedInput[0].toLowerCase()){
                    case "uci":
                        //engine name and author
                        printStdout("id name " + engineName);
                        printStdout("id author " + engineAuthor);
                        //options if there were any

                        //uciok
                        printStdout("uciok");
                        break;
                    case "setoption":
                        //ignore for the moment
                        break;
                    case "isready":
                        //no initialization needed here at the moment so indicate engine is ready
                        printStdout("readyok");
                        break;
                    case "ucinewgame":
                        //ignore
                        break;
                    case "position":
                        //get position
                        if(splittedInput.length > 1){
                            this.position = splittedInput[1];

                            //get moves
                            if(splittedInput.length > 3){
                                if(splittedInput[2].equals("moves")){
                                    this.moves = Arrays.copyOfRange(splittedInput, 3, splittedInput.length);
                                }
                            }
                        }
                        //reset all values needed for the calculation
                        isStopped.set(false);
                        isCalculated.set(false);
                        //computing is started with the go command
                        break;
                    case "go":
                        //ignore params for the moment, start computing async by creating organizer with given values
                        organizer = new Organizer(new Options(), position, new ArrayList<String>(List.of(moves)));
                        CompletableFuture<String> futureMove = organizer.calculateNextMoveAsync();
                        futureMove.thenAccept(s ->
                        {
                            //if(isStopped.get()){//if calculations were stopped by the engine print the move directly
                                printStdout("bestmove " + s);
                            //}else{//else save the move and indicate calcualtion is finished
                                //calculatedMove = s;
                                //isCalculated.set(true);
                            //}
                        });
                        break;
                    case "stop":
                        //indicate gui asked to send the move
                        isStopped.set(true);

                        //if move is calculated print it
                        if(isCalculated.get()){
                            printStdout("bestmove " + calculatedMove);
                        }
                        break;
                    case "quit":
                        //shutdown engine
                        isRunning = false;
                        break;
                    default:
                        if(splittedInput.length > 1){
                            splittedInput = Arrays.copyOfRange(splittedInput, 1, splittedInput.length);
                            continue;
                        }
                }
                //leave the loop if input is recognized
                break;
            }

        }
    }

    public static synchronized void printStdout(String message){
        System.out.println(message);
    }

}
