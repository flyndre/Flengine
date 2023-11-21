package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.moveprovider.MinMax;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class MinMaxTest {

    @Test
    void testPawnMoves() throws ExecutionException, InterruptedException {

        Board board = Converter.convertStringToBoard("8/8/3k4/1B6/8/8/1K6/3r1q2 b - - 0 1");
        MinMax minmax = new MinMax();

        List<Move> moves = minmax.getRecommendedMoves(board);
        assertFalse(moves.isEmpty());
    }
}
