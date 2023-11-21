package de.flyndre.flengine.converter;

import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.datamodel.enums.optionenums.EngineDifficulty;

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
                        logger.info("Sent name and author to gui.");
                        //options
                        ////difficulty value
                        StringBuilder difficultyOptions = new StringBuilder("option name Difficulty type combo default Normal");
                        for(EngineDifficulty e: EngineDifficulty.values()){
                            difficultyOptions.append(" var ").append(e.name());
                        }
                        StdoutWriter.writeToStdout(difficultyOptions.toString());
                        logger.info("Indicated difficulty option to gui: " + difficultyOptions.toString());

                        //uciok
                        StdoutWriter.writeToStdout("uciok");
                        logger.info("Finished initial communication with gui.");
                        break;
                    case "setoption":
                        //read the given option and change the value in the options object accordingly
                        logger.info("Recognized setoption command from gui.");
                        if(splittedInput.length > 4){
                            switch (splittedInput[2]) {
                                case "Difficulty" -> {
                                    this.options.setEngineDifficulty(EngineDifficulty.valueOf(splittedInput[3]));
                                    logger.info("Changed option difficulty to " + splittedInput[3] + ".");
                                }
                            }
                        }
                        break;
                    case "isready":
                        //no initialization needed here at the moment so indicate engine is ready
                        logger.info("Recognized isready from gui.\nSending readyok for synchronizing.");
                        StdoutWriter.writeToStdout("readyok");
                        break;
                    case "ucinewgame":
                        //ignore
                        logger.info("Recognized ucinewgame.");
                        break;
                    case "position":
                        //get position
                        logger.info("Recognized position command.");
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
                        logger.info("Recognized go command.\nStarting calculation...");
                        organizer = new Organizer(this.options, position, new ArrayList<String>(List.of(moves)));
                        CompletableFuture<String> futureMove = organizer.calculateNextMoveAsync();
                        futureMove.thenAccept(s ->
                        {
                            logger.info("Calculation finished.\nBest move: " + s);
                            StdoutWriter.writeToStdout("bestmove " + s);
                        });
                        break;
                    case "stop":
                        //indicate gui asked to send the move
                        //not used yet
                        logger.info("Recognized stop command.");
                        break;
                    case "quit":
                        //shutdown engine
                        logger.info("Recognized quit command.\nShutting down engine.");
                        organizer.stopCalulations();
                        isRunning = false;
                        break;
                    default:
                        if(splittedInput.length > 1){
                            logger.info("Unrecognized command " + splittedInput[0] + ".\nTrying to parse next input.");
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
