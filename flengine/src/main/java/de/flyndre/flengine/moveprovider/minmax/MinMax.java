package de.flyndre.flengine.moveprovider.minmax;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.rules.Rule;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Logger;

/**
 * An implementation of {@code MoveProvider} that uses the Minimax-Algorithm to calculate the best move.
 * The calculation is being calculated by multithreading.
 * @author Ruben
 */
public class MinMax implements MoveProvider {
    Rule legalMoveProvider = new Rule();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Provides a list of recommended {@code Move}s for the given situation on the {@code Board}.
     * @param board The current board.
     * @return A list of recommended moves in this situation which may be empty if none were found.
     */
    @Override
    public List<Move> getRecommendedMoves(Board board, Options options) {
        logger.info("Starting Calculation of Minimax.");
        List<Move> availableMoves = legalMoveProvider.getLegalMoves(board, board.getNextColor());


        HashMap<Move, Integer> evaluatedMoves = new HashMap<>();
        HashMap<Move, ForkJoinTask<Integer>> taskHashMap = new HashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-2);

        availableMoves.forEach(move -> {
            RecursiveMinMaxTask task = new RecursiveMinMaxTask(board, move, 1, board.getNextColor(), options.getRecursionDepth());
            ForkJoinTask<Integer> runningTask = forkJoinPool.submit(task);
            taskHashMap.put(move, task);
        });

        taskHashMap.forEach((key, value) -> evaluatedMoves.put(key, value.join()));
        return evaluatedMoves.keySet().stream().sorted(Comparator.comparing(evaluatedMoves::get)).toList();

    }
}
