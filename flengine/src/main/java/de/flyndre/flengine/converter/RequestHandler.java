package de.flyndre.flengine.converter;

import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.datamodel.enums.Difficulty;
import de.flyndre.flengine.logging.LogChannelManager;
import de.flyndre.flengine.logging.LogChannelType;

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
                        StringBuilder difficultyOptions = new StringBuilder("option name Difficulty type combo default " + Difficulty.NORMAL.toReadableString());
                        for(Difficulty e: Difficulty.values()){
                            difficultyOptions.append(" var ").append(e.toReadableString());
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
                        if (splittedInput.length > 4) {
                            switch (splittedInput[2]) {
                                case "Difficulty" -> {
                                    try {
                                        var difficulty = Difficulty.valueOf(splittedInput[4].toUpperCase());
                                        this.options.setDifficulty(difficulty);
                                        logger.info("Changed option difficulty to [" + difficulty + "].");
                                    } catch (IllegalArgumentException e) {
                                        logger.warning("The value [" + splittedInput[4] + "] is not a valid difficulty.");
                                    }
                                }
                                default -> logger.warning("The value [" + splittedInput[2] + "] is not a supported option.");
                            }
                        }
                        break;
                    case "isready":
                        //no initialization needed here at the moment so indicate engine is ready
                        logger.info("Recognized isready from gui, sending readyok for synchronizing.");
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
                        logger.info("Recognized go command. Starting calculation...");
                        organizer = new Organizer(this.options, position, new ArrayList<String>(List.of(moves)));
                        CompletableFuture<String> futureMove = organizer.calculateNextMoveAsync();
                        futureMove.thenAccept(s ->
                        {
                            logger.info("Calculation finished. Best move: " + s);
                            StdoutWriter.writeToStdout("bestmove " + s);
                        });
                        break;
                    case "stop":
                        //indicate gui asked to send the move
                        //not used yet
                        logger.info("Recognized stop command.");
                        break;
                    case "debug":
                        //change the debug option
                        logger.info("Recognized debug command.");
                        if (splittedInput.length > 1) {
                            if (splittedInput[1].equals("on")) {
                                LogChannelManager.setOpen(LogChannelType.UCI, true);
                                logger.info("Set debug mode to [on].");
                            } else if (splittedInput[1].equals("off")) {
                                LogChannelManager.setOpen(LogChannelType.UCI, false);
                                logger.info("Set debug mode to [off].");
                            } else {
                                logger.warning("The value [" + splittedInput[1] + "] is not a valid value for debug.");
                            }
                        }
                        break;
                    case "quit":
                        //shutdown engine
                        logger.info("Recognized quit command. Shutting down engine.");
                        if (organizer != null)
                            organizer.stopCalulations();
                        isRunning = false;
                        break;
                    default:
                        if(splittedInput.length > 1){
                            logger.info("Unrecognized command " + splittedInput[0] + ", trying to parse next input.");
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
