package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;

import java.util.List;

/**
 * Provides functionality to obtain recommended moves for a given chess situation.
 * @author David
 */
public interface MoveProvider {
    /**
     * Provides a list of recommended {@code Move}s for the given situation on the {@code Board}.
     * @param board The current board.
     * @return A list of recommended moves in this situation which may be empty if none were found.
     */
    List<Move> getRecommendedMoves(Board board, Options options);
}
