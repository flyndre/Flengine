package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;
import de.flyndre.flengine.rules.PieceRule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceRuleTest {

    private final PieceRule PIECERULE = new PieceRule();

    // https://www.dailychess.com/chess/chess-fen-viewer.php
    // alternativ: http://www.netreal.de/Forsyth-Edwards-Notation/index.php

    @Test
    public void testPawnRules() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/pppp1ppp/2P1p3/8/8/8/PP1PPPPP/RNBQKBNR w KQkq - 0 1");
        Field field = new Field(Line.SEVEN, Row.D);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(3, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.C))));
    }

    @Test
    public void testPawnPromotion() {

        Board board = Converter.convertStringToBoard("r1bqkbnr/pPpppppp/2n5/8/8/8/PP1PPPPP/RNBQKBNR w KQkq - 0 1");
        Field field = new Field(Line.SEVEN, Row.B);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(3, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.EIGHT, Row.B), Type.QUEEN)));
        assertTrue(moves.contains(new Move(field, new Field(Line.EIGHT, Row.A), Type.QUEEN)));
        assertTrue(moves.contains(new Move(field, new Field(Line.EIGHT, Row.C), Type.QUEEN)));
    }

    @Test
    public void testRookRules() {

        Board board = Converter.convertStringToBoard("r1bqkbnr/p1pppppp/1p6/8/1R1P4/1n6/PPPP1PPP/1NBQKBNR w KQkq - 0 1");
        Field field = new Field(Line.FOUR, Row.B);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(5, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.C))));
    }

    @Test
    public void testKnightRules() {

        Board board = Converter.convertStringToBoard("r1bqkbnr/ppp1pppp/8/2n5/3p4/1N6/PPPPPPPP/RB1QKBNR w KQkq - 0 1");
        Field field = new Field(Line.THREE, Row.B);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(4, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.ONE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.A))));
    }

    @Test
    public void testBishopRules() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/pppp1ppp/4p3/8/2B5/3P4/PPP1PPPP/RN1QKBNR w KQkq - 0 1");
        Field field = new Field(Line.FOUR, Row.C);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(5, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.E))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
    }

    @Test
    public void testQueenRules() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/p1p1pppp/1p1p4/8/1Q2P3/2P5/PP1P1PPP/RNB1KBNR w KQkq - 0 1");
        Field field = new Field(Line.FOUR, Row.B);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(10, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.A))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.B))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.C))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.SIX, Row.D))));
    }

    @Test
    public void testKingRules() {

        Board board = Converter.convertStringToBoard("rnbqkbnr/ppp2ppp/8/3pp3/4K3/4PP2/PPPP2PP/RNBQ1BNR w KQkq - 0 1");
        Field field = new Field(Line.FOUR, Row.E);

        List<Move> moves = PIECERULE.getLegalMoves(board, field);

        assertEquals(6, moves.size());
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.E))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FIVE, Row.F))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(field, new Field(Line.FOUR, Row.F))));
        assertTrue(moves.contains(new Move(field, new Field(Line.THREE, Row.D))));
    }

    @Test
    public void testIsFieldCovered() {

        assertTrue(true);
    }
}
