package de.flyndre.flengine.converter;


import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.controller.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class organizes the calculation of a next best move to a given cess board.
 * It converts the string representation to the engine data model and provides a method to calculate a next move.
 * @author Lukas
 */
public class Organizer {
    private Options options;
    private Board board;
    private String boardString;
    private List<String> moveStrings;
    private ExecutorService executor;

    /**
     * Constructor to initialize a new Organizer.
     * @param options options that affect the processing.
     * @param board a fence string that represents a chess board.
     * @param moves a list of moves to be played on the board to update it.
     */
    public  Organizer(Options options, String board, List<String> moves){
        this.options = options;
        this.boardString = board;
        this.moveStrings = moves;
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Calculate the next best move to be executed on the actual board.
     * @return a completable future that returns the move in string notation when the calculation is done.
     */
    public CompletableFuture<String> calculateNextMoveAsync() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        executor.submit(()->completableFuture.complete(calculateNextMove()));
        completableFuture.whenCompleteAsync((a, b) -> this.executor.close());
        return completableFuture;
    }
    private String calculateNextMove(){
        this.board = Converter.convertStringToBoard(boardString);
        moveStrings.forEach(move -> board.playMove(Converter.convertStringToMove(move)));
        Move bestMove = Controller.giveMove(board,options);
        //maybe execute the move on the board to provide persistent.
        return Converter.convertMoveToString(bestMove);
    }

    /**
     * stops the executor service with the calculation tasks
     */
    public void stopCalulations(){
        executor.shutdownNow();
    }
}
