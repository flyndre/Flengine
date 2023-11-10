package de.flyndre.flengine.enginecontroller;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.Stub;
import de.flyndre.flengine.rules.Rule;
import java.util.Arrays;
import java.util.List;

/**
 * The centerpiece of Flengine.
 * Orchestrates the move-determination-process by retrieving moves from a list of {@code MoveProviders}.
 * @author David
 */
public class Controller {

    private final static List<MoveProvider> moveProviderHierarchy = Arrays.asList(
            new Stub(new Rule())
            /* Insert other move providers here. */
    );

    /**
     * Determines the best move based on the given {@code Board} and {@code Options}.
     * @param board The current board.
     * @param options An object containing options about the move obtainment process.
     * @return A single {@code Move}, which the engine determined as best possible move or {@code null}, if no move was found.
     */
    public static Move giveMove(Board board, Options options) {
        for (var moveProvider : moveProviderHierarchy) {
            var moves = moveProvider.getRecommendedMoves(board);
            if (moves != null && !moves.isEmpty()) return moves.get((int) Math.floor(Math.random() * moves.size()));
        }
        return null;
    }
}
