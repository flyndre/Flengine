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

        availableMoves.forEach(move -> {
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(board, move, 1);
            ForkJoinTask<Integer> runningTask = forkJoinPool.submit(task);
            taskHashMap.put(move, task);
        });

        taskHashMap.forEach((key, value) -> evaluatedMoves.put(key, value.join()));
        return evaluatedMoves.keySet().stream().sorted(Comparator.comparing(evaluatedMoves::get)).toList();

    }
}
