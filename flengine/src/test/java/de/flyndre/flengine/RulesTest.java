package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.rules.PieceRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesTest {

    private final Line[] LINES = Line.values();
    private final Row[] ROWS = Row.values();

    private PieceRule pieceRule = new PieceRule();
    // https://www.dailychess.com/chess/chess-fen-viewer.php

    @Test
    public void testPawnRules() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/pppp1ppp/2P1p3/8/8/8/PP1PPPPP/RNBQKBNR w KQkq - 0 1");

        List<Move> moves = pieceRule.getLegalMoves(board, new Field(Line.SEVEN, Row.D));

        assertEquals(3, moves.size());
        assertTrue(moves.contains(new Move(new Field(Line.SEVEN, Row.D), new Field(Line.SIX, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.SEVEN, Row.D), new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.SEVEN, Row.D), new Field(Line.SIX, Row.C))));
    }
}
