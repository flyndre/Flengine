package de.flyndre.flengine.moveprovider.minmax;

import de.flyndre.flengine.datamodel.*;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Type;
import de.flyndre.flengine.rules.Rule;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

import static de.flyndre.flengine.datamodel.enums.Color.BLACK;
import static de.flyndre.flengine.datamodel.enums.Color.WHITE;

/**
 * An implementation of {@code RecursiveTask} that calculates the best move for a given Position.
 * The Class uses a ForkJoinPool to handle multiple Threads.
 * The Maximum Depth of the Minimax Algorithm is 3. It can be changed by editing the Variable {@code MAXLEVEL}
 * @author Ruben
 */
public class RecursiveMinMaxTask extends RecursiveTask<Integer> {

    Board board;
    Move move;
    volatile int currentLevel;
    private final Color playerColor;
    private final int MAXLEVEL;
    private final Rule legalMoveProvider = new Rule();

    public RecursiveMinMaxTask(Board board, Move move, int currentLevel, Color playerColor, int recursiveLevel){
        this.board = board;
        this.move = move;
        this.currentLevel = currentLevel;
        this.playerColor = playerColor;
        this.MAXLEVEL = recursiveLevel;
    }

    /**
     * Implementation of the {@code compute}-Method which provides the Rating of a given {@code Move} on a {@code Board}.
     * @return the Rating of the Move.
     */
    @Override
    protected Integer compute() {
        Board newBoard = board.deepClone();

        if(board.getPiece(move.getTo()) != null && board.getPiece(move.getTo()).getTypeOfFigure() == Type.KING){
            return -100000;
        }

        int rating = rateMove(newBoard, move);
        newBoard.playMove(move);

        if(currentLevel == MAXLEVEL){
            return rating;
        }

        List<Move> legalMoves = legalMoveProvider.getLegalMoves(newBoard, board.getNextColor());
        HashMap<Move, Integer> ratedMoves = new HashMap<>();
        HashMap<Move, ForkJoinTask<Integer>> taskHashMap = new HashMap<>();

        legalMoves.forEach(move -> {
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(newBoard, move, currentLevel+1, playerColor, MAXLEVEL);
            ForkJoinTask<Integer> runningTask = getPool().submit(task);
            taskHashMap.put(move, runningTask);
        });

        taskHashMap.forEach((key, value) -> ratedMoves.put(key, value.join()));
        Optional<Map.Entry<Move, Integer>> bestOptionalMove = ratedMoves.entrySet().stream().max(Map.Entry.comparingByValue());
        return bestOptionalMove.map(moveIntegerEntry -> rating + moveIntegerEntry.getValue()).orElse(-100);
    }

    /**
     * Helper-Method that actually calculates the Rating of the Move.
     * @return the Rating of the Move.
     */
    public int rateMove(Board board, Move move){

        Board deepCopiedBoard = board.deepClone();

        int evaluation = 0;
        Field f = move.getTo();
        Piece pieceToHit = deepCopiedBoard.getPiece(f);

        if(deepCopiedBoard.getNextColor() == playerColor){
            evaluation -= (pieceToHit == null) ? 0 : (pieceToHit.getTypeOfFigure().getValue());
        }else{
            evaluation += (pieceToHit == null) ? 0 : (pieceToHit.getTypeOfFigure().getValue());
        }

        if(legalMoveProvider.isChecked(deepCopiedBoard, playerColor)) {
            evaluation += 1;
        }
        if(legalMoveProvider.isChecked(deepCopiedBoard, getOppositeColor(playerColor))) {
            evaluation -= 1;
        }
        if(legalMoveProvider.isCheckmated(deepCopiedBoard, playerColor)){
                evaluation += 100;
        }
        if(legalMoveProvider.isCheckmated(deepCopiedBoard, getOppositeColor(playerColor))){
                evaluation -= 100;
        }

        return evaluation;
    }

    /**
     * Helper-Method returns the opposite Color of which it was given.
     * @return the opposite Color.
     */
    public Color getOppositeColor(Color color){
        return color == BLACK ? WHITE : BLACK;
    }
}
