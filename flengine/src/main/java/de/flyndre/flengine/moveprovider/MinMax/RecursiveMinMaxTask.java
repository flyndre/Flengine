package de.flyndre.flengine.moveprovider.MinMax;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.rules.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class RecursiveMinMaxTask extends RecursiveTask<Integer> {

    Board board;
    Move move;
    volatile int currentLevel;

    private Color playerColor;
    private final int MAXLEVEL = 5;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Rule legalMoveProvider = new Rule();

    public RecursiveMinMaxTask(Board board, Move move, int currentLevel){
        this.board = board;
        this.move = move;
        this.currentLevel = currentLevel;
        this.playerColor = board.getNextColor();
    }

    @Override
    protected Integer compute() {

        Board newBoard = board.deepClone();
        int judgement = judgeMove(board, move);

        logger.info(String.format("Computing move [%s]. Level of Minimax: [%d]. Judgement of Move: [%d]", move.toString(), currentLevel, judgement));

        if(currentLevel == MAXLEVEL){
            return judgement;
        }

        newBoard.playMove(move);
        List<Move> legalMoves = legalMoveProvider.getLegalMoves(newBoard, board.getNextColor());
        HashMap<Move, Integer> judgedMoves = new HashMap<>();
        HashMap<Move, ForkJoinTask<Integer>> taskHashMap = new HashMap<>();

        legalMoves.forEach(move -> {
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(newBoard, move, currentLevel+1);
            ForkJoinTask<Integer> runningTask = getPool().submit(task);
            taskHashMap.put(move, runningTask);
        });

        taskHashMap.forEach((key, value) -> judgedMoves.put(key, value.join()));
        return judgement + judgedMoves.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
    }

    public int judgeMove(Board board, Move move){
        int evaluation = 0;
        Field f = move.getTo();
        Piece pieceToHit = board.getPiece(f);

        if(board.getNextColor() == playerColor){
            evaluation += (pieceToHit == null) ? 0 : (pieceToHit.getTypeOfFigure().getValue());
        }else{
            evaluation -= (pieceToHit == null) ? 0 : (pieceToHit.getTypeOfFigure().getValue());
        }
        return evaluation;
    }
}
