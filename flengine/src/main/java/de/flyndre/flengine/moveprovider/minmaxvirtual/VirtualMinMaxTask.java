package de.flyndre.flengine.moveprovider.minmaxvirtual;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Type;
import de.flyndre.flengine.rules.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static de.flyndre.flengine.datamodel.enums.Color.BLACK;
import static de.flyndre.flengine.datamodel.enums.Color.WHITE;

public class VirtualMinMaxTask implements Callable<Integer> {
    Board board;
    Move move;
    private int currentLevel;
    private final Color playerColor;
    private final int MAXLEVEL;
    private final Rule legalMoveProvider = new Rule();
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public VirtualMinMaxTask(Board board, Move move, int currentLevel, Color playerColor, int recursiveLevel, ExecutorService executorService){
        this.board = board;
        this.move = move;
        this.currentLevel = currentLevel;
        this.playerColor = playerColor;
        this.MAXLEVEL = recursiveLevel;
        this.executorService = executorService;
    }


    /**
     * Implementation of the recursive minimax algorithm based on virtual threads
     * @return the Rating of the Move.
     */
    @Override
    public Integer call() {
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
        HashMap<Move, Future<Integer>> taskHashMap = new HashMap<>();

        legalMoves.forEach(move -> {
            VirtualMinMaxTask task = new VirtualMinMaxTask(newBoard, move, currentLevel+1, playerColor, this.MAXLEVEL, this.executorService);
            Future<Integer> future = executorService.submit(task);
            taskHashMap.put(move, future);
        });

        taskHashMap.forEach((key, value) -> {
            try {
                ratedMoves.put(key, value.get());
            } catch (InterruptedException e) {
                logger.severe(e.getMessage());
            } catch (ExecutionException e) {
                logger.severe(e.getMessage());
            }
        });
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
