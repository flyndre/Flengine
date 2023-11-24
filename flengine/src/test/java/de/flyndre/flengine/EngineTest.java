package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.enginecontroller.Controller;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EngineTest {
    @Test
    void testPawnMoves() throws ExecutionException, InterruptedException {

        ArrayList<Board> boards = new ArrayList<>();

        boards.add(Converter.convertStringToBoard("rnbqkbnr/pppp1ppp/4p3/3P4/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1"));
        boards.add(Converter.convertStringToBoard("rnbqkbnr/pp1ppppp/2p5/1B6/3PPB2/8/PPP2PPP/RN1QK1NR b KQkq - 0 1"));
        boards.add(Converter.convertStringToBoard("3k4/8/8/8/8/8/8/1R1K4 w - - 0 1"));
        boards.add(Converter.convertStringToBoard("1q1k4/8/8/8/8/8/8/1R1K2b1 w - - 0 1"));
        boards.add(Converter.convertStringToBoard("1q1k4/8/4n3/8/3B4/8/8/1R1K2b1 b - - 0 1"));
        boards.add(Converter.convertStringToBoard("1q1k4/8/4n3/4p3/3B4/8/8/1R1K2b1 b - - 0 1"));
        boards.add(Converter.convertStringToBoard("1q1k4/8/4n3/8/1R1B4/8/4p3/K5b1 b - - 0 1"));

        boards.forEach(board -> {
            Move move = Controller.giveMove(board, new Options());
            assertNotNull(move);
        });
    }
}
