package de.flyndre.flengine;

import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RuleTest {

    private final Rule RULE = new Rule();

    // https://www.dailychess.com/chess/chess-fen-viewer.php
    // alternativ: http://www.netreal.de/Forsyth-Edwards-Notation/index.php

    @Test
    public void testMovesInCheck() {

        Board board = Converter.convertStringToBoard("6k1/6b1/4N3/2B5/8/1R4Q1/2PPP3/K7 w KQkq - 0 1");

        List<Move> moves = RULE.getLegalMoves(board, Color.WHITE);

        assertEquals(12, moves.size());
        assertTrue(moves.contains(new Move(new Field(Line.ONE, Row.A), new Field(Line.TWO, Row.A))));
        assertTrue(moves.contains(new Move(new Field(Line.ONE, Row.A), new Field(Line.ONE, Row.B))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.B), new Field(Line.TWO, Row.B))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.B), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.C), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.TWO, Row.D), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.C), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.THREE, Row.C))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.FIVE, Row.E))));
        assertTrue(moves.contains(new Move(new Field(Line.THREE, Row.G), new Field(Line.SEVEN, Row.G))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.E), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.E), new Field(Line.SEVEN, Row.G))));
    }

    @Test
    public void testInPin() {

        Board board = Converter.convertStringToBoard("7b/2b1r1R1/3PB3/rB2Kp2/4P3/8/4q3/3k4 w KQkq - 0 1");

        List<Move> moves = RULE.getLegalMoves(board, Color.WHITE);

        assertEquals(6, moves.size());
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FOUR, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FOUR, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FIVE, Row.D))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.FIVE, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.FIVE, Row.E), new Field(Line.SIX, Row.F))));
        assertTrue(moves.contains(new Move(new Field(Line.SIX, Row.D), new Field(Line.SEVEN, Row.C))));
    }
}
