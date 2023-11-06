package de.flyndre.flengine.enginecontroller;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.Stub;

import java.util.Arrays;
import java.util.List;

public class Controller {

    private final static List<MoveProvider> moveProviderHierarchy = Arrays.asList(
            new Stub()
            /* Insert other move providers here. */
    );

    /**
     *
     * @param board The current Board.
     * @param options An Object containing options about the move obtainment process.
     * @return A single {@code Move}, which the engine determined as best possible move or {@code null}, if no move was found.
     */
    public static Move giveMove(Board board, Options options) {
        for (var moveProvider : moveProviderHierarchy) {
            var moves = moveProvider.getRecommendedMoves(board, board.getNextColor());
            if (!moves.isEmpty()) return moves.get(0);
        }
        return null;
    }
}
