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
    void testEnPassant() {

        Board board = Converter.convertStringToBoard("4k3/8/8/3pPp2/8/8/8/4K3 w - d6 0 1");
        Field field = new Field(Line.FIVE, Row.E);

        List<Move> moves = rule.getLegalMoves(board, Color.WHITE);
        board.playMove(new Move(field, new Field(Line.SIX, Row.F)));

        assertFalse(moves.contains(new Move(field, new Field(Line.SIX, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.F))));
        assertNull(board.getPiece(field));
        assertNull(board.getPiece(new Field(Line.FIVE, Row.F)));
        assertEquals(new Piece(Type.PAWN, Color.WHITE), board.getPiece(new Field(Line.SIX, Row.F)));
    }

    @Test
    void testKingsideCastleMoves() {

        Board board = Converter.convertStringToBoard("r3k2r/p6p/8/4B3/8/8/P6P/R3K2R w KQkq - 0 1");

        List<Move> movesWhite = rule.getLegalMoves(board, new Field(Line.ONE, Row.E));
        List<Move> movesBlack = rule.getLegalMoves(board, new Field(Line.EIGHT, Row.E));
        board.playMove(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.G)));

        assertTrue(movesWhite.contains(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.C))));
        assertTrue(movesWhite.contains(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.G))));
        assertFalse(movesBlack.contains(new Move(new Field(Line.EIGHT, Row.E), new Field(Line.EIGHT, Row.C))));
        assertFalse(movesBlack.contains(new Move(new Field(Line.EIGHT, Row.E), new Field(Line.EIGHT, Row.G))));
        assertNull(board.getPiece(new Field(Line.ONE, Row.E)));
        assertNull(board.getPiece(new Field(Line.ONE, Row.H)));
        assertEquals(board.getPiece(new Field(Line.ONE, Row.F)), new Piece(Type.ROOK, Color.WHITE));
        assertEquals(board.getPiece(new Field(Line.ONE, Row.G)), new Piece(Type.KING, Color.WHITE));
    }

    @Test
    void testQueensideCastleMoves() {

        Board board = Converter.convertStringToBoard("r3k3/p6r/7p/8/8/8/P6P/R3K2R w KQ - 0 1");

        List<Move> movesWhite = rule.getLegalMoves(board, new Field(Line.ONE, Row.E));
        List<Move> movesBlack = rule.getLegalMoves(board, new Field(Line.EIGHT, Row.E));
        board.playMove(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.C)));

        assertTrue(movesWhite.contains(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.C))));
        assertTrue(movesWhite.contains(new Move(new Field(Line.ONE, Row.E), new Field(Line.ONE, Row.G))));
        assertFalse(movesBlack.contains(new Move(new Field(Line.EIGHT, Row.E), new Field(Line.EIGHT, Row.C))));
        assertFalse(movesBlack.contains(new Move(new Field(Line.EIGHT, Row.E), new Field(Line.EIGHT, Row.G))));
        assertNull(board.getPiece(new Field(Line.ONE, Row.E)));
        assertNull(board.getPiece(new Field(Line.ONE, Row.A)));
        assertEquals(board.getPiece(new Field(Line.ONE, Row.C)), new Piece(Type.KING, Color.WHITE));
        assertEquals(board.getPiece(new Field(Line.ONE, Row.D)), new Piece(Type.ROOK, Color.WHITE));
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
}
