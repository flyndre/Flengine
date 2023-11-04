package de.flyndre.flengine.rules;

import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;

public class BoardUtil {

    public static int toInt(Line line) {

        return switch (line) {
            case ONE -> 0;
            case TWO -> 1;
            case THREE -> 2;
            case FOUR -> 3;
            case FIVE -> 4;
            case SIX -> 5;
            case SEVEN -> 6;
            case EIGHT -> 7;
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't convert line %s to integer", line
            ));
        };
    }

    public static int toInt(Row row) {

        return switch (row) {
            case A -> 0;
            case B -> 1;
            case C -> 2;
            case D -> 3;
            case E -> 4;
            case F -> 5;
            case G -> 6;
            case H -> 7;
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't convert row %s to integer", row
            ));
        };
    }

    public static Line toLine(int line) {

        return switch (line) {
            case 0 -> Line.ONE;
            case 1 -> Line.TWO;
            case 2 -> Line.THREE;
            case 3 -> Line.FOUR;
            case 4 -> Line.FIVE;
            case 5 -> Line.SIX;
            case 6 -> Line.SEVEN;
            case 7 -> Line.EIGHT;
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't convert line %s to integer", line
            ));
        };
    }

    public static Row toRow(int row) {

        return switch (row) {
            case 0 -> Row.A;
            case 1 -> Row.B;
            case 2 -> Row.C;
            case 3 -> Row.D;
            case 4 -> Row.E;
            case 5 -> Row.F;
            case 6 -> Row.G;
            case 7 -> Row.H;
            default -> throw new IllegalArgumentException(String.format(
                "Couldn't convert integer %s to row", row
            ));
        };
    }
}
