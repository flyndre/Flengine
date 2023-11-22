package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.moveprovider.minmax.MinMax;
import de.flyndre.flengine.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class MinMaxTest {

    @Test
    void testPawnMoves() throws ExecutionException, InterruptedException {

        Board board = Converter.convertStringToBoard("r1bqkbnr/pp1ppppp/2n5/8/2PQP3/8/PP3PPP/RNB1KBNR b KQkq - 0 4");
        MinMax minmax = new MinMax();
        Rule rule = new Rule();
        List<Move> moves = minmax.getRecommendedMoves(board);
        assertFalse(moves.isEmpty());
    }
}
