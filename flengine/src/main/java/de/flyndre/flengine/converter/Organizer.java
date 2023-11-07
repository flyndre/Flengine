package de.flyndre.flengine.converter;


import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.enginecontroller.Controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Organizer {
    private Options options;
    private Board board;
    private String boardString;
    private List<String> moveStrings;
    private ExecutorService executor;

    public  Organizer(Options options, String board, List moves){
        this.options = options;
        this.boardString = board;
        this.moveStrings = moves;
        executor = Executors.newSingleThreadExecutor();
    }
    public Future<String> calculateNextMoveAsync() {
        return  executor.submit(()->calculateNextMove());
    }
    private String calculateNextMove(){
        this.board = Converter.convertStringToBoard(boardString);
        moveStrings.forEach(move -> board.playMove(Converter.convertStringToMove(move)));
        Move bestMove = Controller.giveMove(board,options);
        //maybe execute the move on the board to provide persistent.
        return Converter.convertMoveToString(bestMove);
    }
}
