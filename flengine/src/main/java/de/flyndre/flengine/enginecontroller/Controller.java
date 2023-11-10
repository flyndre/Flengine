package de.flyndre.flengine.enginecontroller;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.Endgame;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.Openings;
import de.flyndre.flengine.moveprovider.Stub;
import de.flyndre.flengine.rules.Rule;
import de.flyndre.flengine.util.FileLogger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * The centerpiece of Flengine.
 * Orchestrates the move-determination-process by retrieving moves from a list of {@code MoveProviders}.
 * @author David
 */
public class Controller {

    private static final Logger logger = FileLogger.getLogger("Controller");

    private static final List<MoveProvider> moveProviderHierarchy = Arrays.asList(
            new Openings(),
            new Endgame(),
            new Stub(new Rule())
    );

    /**
     * Determines the best move based on the given {@code Board} and {@code Options}.
     * @param board The current board.
     * @param options An object containing options about the move obtainment process.
     * @return A single {@code Move}, which the engine determined as best possible move or {@code null}, if no move was found.
     */
    public static Move giveMove(Board board, Options options) {
        for (var moveProvider : moveProviderHierarchy) {
            logger.info("Requesting moves from: [" + moveProvider.getClass().getName() + "]");
            var moves = moveProvider.getRecommendedMoves(board);
            if (moves != null && !moves.isEmpty()){
                logger.info("Received: [" + moves.size() + " moves]");
                return moves.get((int) Math.floor(Math.random() * moves.size()));
            }
        }
        logger.warning("No possible moves were found.");
        return null;
    }
}
