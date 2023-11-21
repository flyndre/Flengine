package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.rules.Rule;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class RecursiveMinMaxTask extends RecursiveTask<Integer> {

    Board board;
    Move move;
    volatile int currentLevel;
    private final int MAXLEVEL = 3;


    Logger logger = Logger.getLogger(this.getClass().getName());
    private final Rule legalMoveProvider = new Rule();

    public RecursiveMinMaxTask(Board board, Move move, int currentLevel){
        this.board = board;
        this.move = move;
        this.currentLevel = currentLevel;
    }
    @Override
    protected Integer compute() {
        ReentrantLock l = new ReentrantLock();

        Board newBoard = board.deepClone();

        logger.info(String.valueOf(currentLevel));
        int judgement = judgeMove(board, move);

        if(currentLevel == MAXLEVEL){
            return judgement;
        }
        else{
            newBoard.playMove(move);
            List<Move> legalMoves = legalMoveProvider.getLegalMoves(newBoard, board.getNextColor());
            HashMap<Move, Integer> availableMoves = new HashMap<>();
            int newLevel = currentLevel + 1;

            for (Move moves : legalMoves) {
                RecursiveMinMaxTask task = new RecursiveMinMaxTask(newBoard, moves, newLevel);
                ForkJoinTask<Integer> abc = getPool().submit(task);
                availableMoves.put(moves, abc.join());
            }

                //Eventuell schauen das Gegnerzug Minuszahl hat und nicht addiert wird
            return judgement + availableMoves.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        }
    }

    public int judgeMove(Board board, Move move){
        int evaluation = 0;
        Field f = move.getTo();


        Piece pieceToHit = board.getPiece(f);
        evaluation += (pieceToHit == null) ? 0 : (pieceToHit.getTypeOfFigure().getValue() - board.getPiece(move.getFrom()).getTypeOfFigure().getValue());
        return evaluation;
    }
}
