package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.moveprovider.MinMax.MinMax;
import de.flyndre.flengine.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class MinMaxTest {

    @Test
    void testPawnMoves() throws ExecutionException, InterruptedException {

        Board board = Converter.convertStringToBoard("r1bnkbnr/pp2pppp/8/1Bpp4/1P1PP3/8/P1P2PPP/R2NKBNR b KQkq - 0 1");
        MinMax minmax = new MinMax();
        Rule rule = new Rule();
        List<Move> moves = minmax.getRecommendedMoves(board);
        assertFalse(moves.isEmpty());
    }
}
