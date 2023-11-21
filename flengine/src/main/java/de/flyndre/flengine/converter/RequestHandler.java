package de.flyndre.flengine.converter;

import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.datamodel.enums.EngineDifficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class RequestHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String engineName = "Flengine";
    private final String engineAuthor = "TeamFlyndre";
    private String position = "";
    private String[] moves = {};
    private Scanner systemInScanner;
    private Organizer organizer;
    private Options options;

    /**
     * Startup for the chess engine
     */
    public void startUp(){
        systemInScanner = new Scanner(System.in);
        options = new Options();
        String input = "";
        boolean isRunning = true;
        logger.info("Engine initialised and ready.");

        while(isRunning){
            input = systemInScanner.nextLine();
            logger.info("Recieved Inputstring: [" + input + "]");
            String[] splittedInput = input.split(" ");

            while(true){
                switch(splittedInput[0].toLowerCase()){
                    case "uci":
                        //engine name and author
                        StdoutWriter.writeToStdout("id name " + engineName);
                        StdoutWriter.writeToStdout("id author " + engineAuthor);
                        //options
                        ////difficulty value
                        StringBuilder difficultyOptions = new StringBuilder("option name Difficulty type combo default Normal");
                        for(EngineDifficulty e: EngineDifficulty.values()){
                            difficultyOptions.append(" var ").append(e.name());
                        }
                        StdoutWriter.writeToStdout(difficultyOptions.toString());

                        //uciok
                        StdoutWriter.writeToStdout("uciok");
                        break;
                    case "setoption":
                        //read the given option and change the value in the options object accordingly
                        if(splittedInput.length > 4){
                            switch (splittedInput[2]) {
                                case "Difficulty" ->
                                        this.options.setEngineDifficulty(EngineDifficulty.valueOf(splittedInput[3]));
                            }
                        }
                        break;
                    case "isready":
                        //no initialization needed here at the moment so indicate engine is ready
                        StdoutWriter.writeToStdout("readyok");
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
                        //computing is started with the go command
                        break;
                    case "go":
                        //ignore params for the moment, start computing async by creating organizer with given values
                        organizer = new Organizer(this.options, position, new ArrayList<String>(List.of(moves)));
                        CompletableFuture<String> futureMove = organizer.calculateNextMoveAsync();
                        futureMove.thenAccept(s ->
                        {
                            StdoutWriter.writeToStdout("bestmove " + s);
                        });
                        break;
                    case "stop":
                        //indicate gui asked to send the move
                        //not used yet
                        break;
                    case "quit":
                        //shutdown engine
                        organizer.stopCalulations();
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
}
