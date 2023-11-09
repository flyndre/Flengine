package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.Move;

import java.util.List;

public interface MoveProvider {
    List<Move> getRecommendedMoves(Board board, Color color);
}
