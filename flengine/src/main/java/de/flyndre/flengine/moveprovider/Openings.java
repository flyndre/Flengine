package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.List;

public class Openings implements MoveProvider {

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    public List<Move> getRecommendedMoves(Board board, Color color) {
        return null;
    }
}
