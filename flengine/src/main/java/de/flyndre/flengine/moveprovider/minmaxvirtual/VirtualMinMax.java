package de.flyndre.flengine.moveprovider.minmaxvirtual;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.minmax.RecursiveMinMaxTask;
import de.flyndre.flengine.rules.Rule;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class VirtualMinMax implements MoveProvider {
    Rule legalMoveProvider = new Rule();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Move> getRecommendedMoves(Board board, Options options) {
        logger.info("Starting Calculation of virtual Minimax.");
        List<Move> availableMoves = legalMoveProvider.getLegalMoves(board, board.getNextColor());

        HashMap<Move, Integer> evaluatedMoves = new HashMap<>();
        HashMap<Move, Future<Integer>> taskHashMap = new HashMap<>();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        availableMoves.forEach(move -> {
            VirtualMinMaxTask task = new VirtualMinMaxTask(board, move, 1, board.getNextColor(), options.getRecursionDepth(), executor);
            Future<Integer> future = executor.submit(task);
            taskHashMap.put(move, future);
        });

        taskHashMap.forEach((key, value) -> {
            try {
                evaluatedMoves.put(key, value.get());
            } catch (InterruptedException e) {
                logger.severe(e.getMessage());
            } catch (ExecutionException e) {
                logger.severe(e.getMessage());
            }
        });
        return evaluatedMoves.keySet().stream().sorted(Comparator.comparing(evaluatedMoves::get)).toList();
    }
}
