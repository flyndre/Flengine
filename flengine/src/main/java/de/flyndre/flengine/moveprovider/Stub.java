package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.rules.Rule;

import java.util.List;

public class Stub implements MoveProvider {

    private final Rule rules;

    public Stub(Rule rules) {
        this.rules = rules;
    }

    @Override
    public List<Move> getRecommendedMoves(Board board, Options options) {
        return rules.getLegalMoves(board, board.getNextColor());
    }
}
