package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.rules.PieceRule;
import de.flyndre.flengine.rules.Rule;
import org.glassfish.json.MapUtil;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MinMax implements MoveProvider  {
    Rule legalMoveProvider = new Rule();
    @Override
    public List<Move> getRecommendedMoves(Board board) {
        List<Move> availableMoves = legalMoveProvider.getLegalMoves(board, board.getNextColor());

        HashMap<Move, Integer> evaluatedMoves = new HashMap<>();
        List<Move> returnMovesList = new ArrayList<>();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        for(Move move : availableMoves){
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(board, move, 1);
            forkJoinPool.invoke(task);
            evaluatedMoves.put(move, task.join());
        }

        for(int i = 0; i < evaluatedMoves.size(); i++){
            Map.Entry<Move, Integer> bestMove = (Map.Entry<Move, Integer>) evaluatedMoves.entrySet().toArray()[0];

            for(Map.Entry<Move, Integer> selection : evaluatedMoves.entrySet()){
                if(selection.getValue() > bestMove.getValue() && !returnMovesList.contains(selection.getKey())){
                    bestMove = selection;
                }
            }
            returnMovesList.add(bestMove.getKey());
        }

        return returnMovesList;
    }
}
