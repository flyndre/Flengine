package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;
import de.flyndre.flengine.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RuleTest {

    private final Rule rule = new Rule();

    // https://www.dailychess.com/chess/chess-fen-viewer.php

    @Test
    void testMovesAtStart() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);

        assertEquals(20, moves.size());
    }

    @Test
    void testMovesInCheck() {

        Board board = Converter.convertStringToBoard("6k1/R5b1/4N3/2B5/8/1R4Q1/1KPPP3/8 w - - 0 1");

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);

        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.B), new Field(Line.TWO, Row.A))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.B), new Field(Line.THREE, Row.A))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.B), new Field(Line.ONE, Row.B))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.B), new Field(Line.ONE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.B), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.C), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.D), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.C), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.FIVE, Row.E))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.SEVEN, Row.G))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.E), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.E), new Field(Line.SEVEN, Row.G))));
        assertTrue(moves.contains(new Move(new Field(Line.SEVEN, Row.A), new Field(Line.SEVEN, Row.G))));
        assertEquals(14, moves.size());
    }

    @Test
    void testMovesWhenPinned() {

        Board board = Converter.convertStringToBoard("1b5b/4r1R1/3BB3/rP2Kp2/4P3/8/4q3/3k4 w - - 0 1");

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);

        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FOUR, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FIVE, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.SIX, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.D), new Field(Line.SEVEN, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.D), new Field(Line.EIGHT, Row.B))));
        assertEquals(7, moves.size());
    }

    @Test
    void testMovesWhenPinnedInCheck() {

        Board board = Converter.convertStringToBoard("3rkr2/4q3/8/8/1b5b/2B3B1/2p3p1/4K3 w - - 0 1");

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);

        assertTrue(moves.isEmpty());
    }

    @Test
    void testKingMoveToOtherKing() {

        Board board = Converter.convertStringToBoard("8/8/4k3/8/4K3/6N1/8/8 w - - 0 1");

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);

        assertFalse(moves.contains(new Move(new Field(Line.FOUR, Row.E), new Field(Line.FIVE, Row.F))));
    }
}
