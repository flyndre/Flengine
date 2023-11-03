package org.flyndre.Zuggeber;

import org.flyndre.DataModel.Board;
import org.flyndre.DataModel.EnumTypes.Color;
import org.flyndre.DataModel.Move;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.List;

public class EndspielZuggeber implements Zuggeber{
    Jsonb jsonb = JsonbBuilder.create();
    @Override
    public List<Move> getRecommendedMoves(Board board, Color color) {
        return null;
    }
}
