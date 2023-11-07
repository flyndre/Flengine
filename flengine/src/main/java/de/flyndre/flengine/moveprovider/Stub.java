package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.rules.Rule;

import java.util.List;

public class Stub implements MoveProvider {

    private Rule rules;

    public Stub(Rule rules) {
        this.rules = rules;
    }

    @Override
    public List<Move> getRecommendedMoves(Board board, Color color) {
        return rules.getLegalMoves(board, color);
    }
}
