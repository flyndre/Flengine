package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import java.util.List;

public class Endgame implements MoveProvider {
    @Override
    public List<Move> getRecommendedMoves(Board board) {
        return null;
    }
}
