package de.flyndre.flengine.enginecontroller;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.Endgame;
import de.flyndre.flengine.moveprovider.minmax.MinMax;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.Openings;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * The centerpiece of Flengine.
 * Orchestrates the move-determination-process by retrieving moves from a list of {@code MoveProviders}.
 * @author David
 */
public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    /**
     * This number is used to tweak the difficulty of the engine.
     * A lower number makes it less difficult, a higher one more difficult.
     * The value 1 gives all moves the same probability.
     */
    private static double DIFFICULTY = 8;

    private static final List<MoveProvider> moveProviderHierarchy = Arrays.asList(
            new Openings(),
            new Endgame(),
            new MinMax()
    );

    /**
     * Determines the best move based on the given {@code Board} and {@code Options}.
     * @param board The current board.
     * @param options An object containing options about the move obtainment process.
     * @return A single {@code Move}, which the engine determined as best possible move or {@code null}, if no move was found.
     */
    public static Move giveMove(Board board, Options options) {
        DIFFICULTY = options.getEngineDifficulty().getDifficultyValue();
        for (var moveProvider : moveProviderHierarchy) {
            logger.info("Requesting moves from: [" + moveProvider.getClass().getName() + "]");
            var moves = moveProvider.getRecommendedMoves(board);
            if (moves != null && !moves.isEmpty()){
                logger.info("Received: [" + moves.size() + " moves]");
                Move bestMove =  moves.get(
                        (int) Math.floor(
                                // Squaring the random number to control which items are more probable:
                                // (1) A higher exponent makes the front items of the list more probable.
                                // (2) Because the front moves should be the better ones, this makes
                                //     the engine more difficult.
                                //
                                // (*) The modulo 1 is needed to prevent values greater than 1 if
                                //     the difficulty is less than 0.
                                Math.pow(Math.random(), DIFFICULTY) % 1 * moves.size()
                    )
                );
                logger.info("Best move is [" + bestMove + "] by [" + moveProvider.getClass().getName() + "]");
                return bestMove;
            }
        }
        logger.warning("No possible moves were found.");
        return null;
    }
}
