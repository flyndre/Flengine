package org.flyndre.Zuggeber;

import org.flyndre.DataModel.Board;
import org.flyndre.DataModel.EnumTypes.Color;
import org.flyndre.DataModel.Move;

import java.util.List;

public interface Zuggeber {
    List<Move> getRecommendedMoves(Board board, Color color);
}
