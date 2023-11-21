package de.flyndre.flengine.moveprovider.MinMax;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.rules.Rule;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class MinMax implements MoveProvider {
    Rule legalMoveProvider = new Rule();
    @Override
    public List<Move> getRecommendedMoves(Board board) {
        List<Move> availableMoves = legalMoveProvider.getLegalMoves(board, board.getNextColor());


        HashMap<Move, Integer> evaluatedMoves = new HashMap<>();
        HashMap<Move, ForkJoinTask<Integer>> taskHashMap = new HashMap<>();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        for(Move move : availableMoves){
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(board, move, 1);
            ForkJoinTask<Integer> runningTask = forkJoinPool.submit(task);
            taskHashMap.put(move, task);
        }

        taskHashMap.forEach((key, value) -> evaluatedMoves.put(key, value.join()));

        List<Move> returnMovesList = new ArrayList<>();
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
