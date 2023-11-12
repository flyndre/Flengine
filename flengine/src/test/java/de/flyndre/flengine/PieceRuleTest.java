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
import de.flyndre.flengine.rules.PieceRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceRuleTest {

    private final PieceRule pieceRule = new PieceRule();

    // https://www.dailychess.com/chess/chess-fen-viewer.php

    @Test
    void testPawnMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/8/8/8/3p1P2/4P3/4K3 w - - 0 1");
        Field field = new Field(Line.TWO, Row.E);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.E))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.E))));
        assertEquals(3, moves.size());
    }

    @Test
    void testPawnPromotion() {

        Board board = Converter.convertStringToBoard("1n2k3/2P5/8/8/8/8/8/4K3 w - - 0 1");
        Field field = new Field(Line.SEVEN, Row.C);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.EIGHT, Row.B), Type.QUEEN)));
        assertTrue(moves.contains(new Move(field, new Field(Line.EIGHT, Row.C), Type.QUEEN)));
        assertEquals(2, moves.size());
    }

    @Test
    void testEnPassant() {

        Board board = Converter.convertStringToBoard("4k3/8/8/3pPp2/8/8/8/4K3 w - d6 0 1");
        Field field = new Field(Line.FIVE, Row.E);

        List<Move> moves = pieceRule.getLegalMoves(board, field);
        board.playMove(new Move(field, new Field(Line.SIX, Row.F)));

        assertFalse(moves.contains(new Move(field, new Field(Line.SIX, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.F))));
        assertNull(board.getPiece(field));
        assertNull(board.getPiece(new Field(Line.FIVE, Row.F)));
        assertEquals(new Piece(Type.PAWN, Color.WHITE), board.getPiece(new Field(Line.SIX, Row.F)));
    }

    @Test
    void testRookMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/1p6/8/1R1P4/1p6/8/4K3 w - - 0 1");
        Field field = new Field(Line.FOUR, Row.B);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.C))));
        assertEquals(5, moves.size());
    }

    @Test
    void testKnightMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/8/2n5/3p4/1N6/8/R3K3 w - - 0 1");
        Field field = new Field(Line.THREE, Row.B);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.ONE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.TWO, Row.D))));
        assertEquals(5, moves.size());
    }

    @Test
    void testBishopMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/4p3/8/2B5/3P4/P7/4K3 w - - 0 1");
        Field field = new Field(Line.FOUR, Row.C);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.E))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
        assertEquals(5, moves.size());
    }

    @Test
    void testQueenMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/1p6/2p5/1Q2P3/2P5/1P6/4K3 w - - 0 1");
        Field field = new Field(Line.FOUR, Row.B);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.D))));
        assertEquals(9, moves.size());
    }

    @Test
    void testKingMoves() {

        Board board = Converter.convertStringToBoard("4k3/8/4b3/4pP2/4K3/3P1p2/8/3b4 w - - 0 1");
        Field field = new Field(Line.FOUR, Row.E);

        List<Move> moves = pieceRule.getLegalMoves(board, field);

        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.E))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.E))));
        assertEquals(2, moves.size());
    }

    @Test
    void testKingsideCastleMoves() {

        Board board = Converter.convertStringToBoard("r3k2r/p6p/8/4B3/8/8/P6P/R3K2R w KQkq - 0 1");

        assertTrue(board.getWhiteLongCastling());
        assertTrue(board.getWhiteShortCastling());
        assertTrue(board.getBlackLongCastling());
        assertTrue(board.getBlackShortCastling());

        List<Move> movesWhite = pieceRule.getLegalMoves(board, new Field(Line.ONE, Row.E));
        List<Move> movesBlack = pieceRule.getLegalMoves(board, new Field(Line.EIGHT, Row.E));
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

        assertTrue(board.getWhiteLongCastling());
        assertTrue(board.getWhiteShortCastling());
        assertFalse(board.getBlackLongCastling());
        assertFalse(board.getBlackShortCastling());

        List<Move> movesWhite = pieceRule.getLegalMoves(board, new Field(Line.ONE, Row.E));
        List<Move> movesBlack = pieceRule.getLegalMoves(board, new Field(Line.EIGHT, Row.E));
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
    void testIsFieldCovered() {

        Board board = Converter.convertStringToBoard("rbrQkb1n/R1p1p2p/2ppp1p1/p4K2/8/PNP5/RBPPP1P1/PN4B1 w - - 0 1");
        Color color = Color.WHITE;

        Line[] LINES = Line.values();
        Row[] ROWS = Row.values();

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[0]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[1]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[2]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[3]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[4]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[0], ROWS[7]), color));

        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[0]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[1]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[2]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[3]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[4]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[6]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[1], ROWS[7]), color));

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[0]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[1]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[2]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[3]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[4]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[6]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[2], ROWS[7]), color));

        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[0]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[1]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[2]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[3]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[4]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[5]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[3], ROWS[7]), color));

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[0]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[1]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[2]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[3]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[4]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[5]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[4], ROWS[7]), color));

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[0]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[1]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[2]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[3]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[4]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[5], ROWS[7]), color));

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[0]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[1]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[2]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[3]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[4]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[6], ROWS[7]), color));

        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[0]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[1]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[2]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[3]), color));
        assertTrue(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[4]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[5]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[6]), color));
        assertFalse(pieceRule.isFieldCovered(board, new Field(LINES[7], ROWS[7]), color));
    }
}
