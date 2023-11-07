package de.flyndre.flengine.converter;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Piece;
import de.flyndre.flengine.datamodel.enums.Color;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;

public class Converter {
    /**
     * Converts a given string to a board object
     * @param input the string to be converted ("startpos" or fenstring)
     * @return the created board object
     */
    public static Board convertStringToBoard(String input){
        Board board = new Board();
        //startposition
        if(input.equals("startpos")){
            //setup white color
            board.setPiece(new Piece(Type.ROOK, Color.WHITE), new Field(Line.ONE, Row.A));
            board.setPiece(new Piece(Type.KNIGHT, Color.WHITE), new Field(Line.ONE, Row.B));
            board.setPiece(new Piece(Type.BISHOP, Color.WHITE), new Field(Line.ONE, Row.C));
            board.setPiece(new Piece(Type.QUEEN, Color.WHITE), new Field(Line.ONE, Row.D));
            board.setPiece(new Piece(Type.KING, Color.WHITE), new Field(Line.ONE, Row.E));
            board.setPiece(new Piece(Type.BISHOP, Color.WHITE), new Field(Line.ONE, Row.F));
            board.setPiece(new Piece(Type.KNIGHT, Color.WHITE), new Field(Line.ONE, Row.G));
            board.setPiece(new Piece(Type.ROOK, Color.WHITE), new Field(Line.ONE, Row.H));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.A));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.B));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.C));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.D));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.E));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.F));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.G));
            board.setPiece(new Piece(Type.PAWN, Color.WHITE), new Field(Line.TWO, Row.H));

            //setup black color
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.A));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.B));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.C));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.D));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.E));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.F));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.G));
            board.setPiece(new Piece(Type.PAWN, Color.BLACK), new Field(Line.SEVEN, Row.H));
            board.setPiece(new Piece(Type.ROOK, Color.BLACK), new Field(Line.EIGHT, Row.A));
            board.setPiece(new Piece(Type.KNIGHT, Color.BLACK), new Field(Line.EIGHT, Row.B));
            board.setPiece(new Piece(Type.BISHOP, Color.BLACK), new Field(Line.EIGHT, Row.C));
            board.setPiece(new Piece(Type.QUEEN, Color.BLACK), new Field(Line.EIGHT, Row.D));
            board.setPiece(new Piece(Type.KING, Color.BLACK), new Field(Line.EIGHT, Row.E));
            board.setPiece(new Piece(Type.BISHOP, Color.BLACK), new Field(Line.EIGHT, Row.F));
            board.setPiece(new Piece(Type.KNIGHT, Color.BLACK), new Field(Line.EIGHT, Row.G));
            board.setPiece(new Piece(Type.ROOK, Color.BLACK), new Field(Line.EIGHT, Row.H));
        }else{//fenstring
            String[] split = input.split(" ");
            String pos = split[0];

            //decode next move color
            if(split[1].equals("w")){
                board.setNextColor(Color.WHITE);
            }else{
                board.setNextColor(Color.BLACK);
            }

            //decode position
            int lineCount = 7;
            split = pos.split("/");
            for(String row: split){
                int rowCount = 0;

                for(char c: row.toCharArray()){
                    Piece piece = null;

                    if(Character.isDigit(c)){//add empty fields on rowCount
                        rowCount = rowCount + (c - '0');
                    }else{//field is not empty
                        if(Character.isUpperCase(c)){
                            switch(Character.toLowerCase(c)){//white
                                case 'r':
                                    piece = new Piece(Type.ROOK, Color.WHITE);
                                    break;
                                case 'n':
                                    piece = new Piece(Type.KNIGHT, Color.WHITE);
                                    break;
                                case 'b':
                                    piece = new Piece(Type.BISHOP, Color.WHITE);
                                    break;
                                case 'q':
                                    piece = new Piece(Type.QUEEN, Color.WHITE);
                                    break;
                                case 'k':
                                    piece = new Piece(Type.KING, Color.WHITE);
                                    break;
                                case 'p':
                                    piece = new Piece(Type.PAWN, Color.WHITE);
                                    break;
                            }
                        }else{
                            switch(Character.toLowerCase(c)){//black
                                case 'r':
                                    piece = new Piece(Type.ROOK, Color.BLACK);
                                    break;
                                case 'n':
                                    piece = new Piece(Type.KNIGHT, Color.BLACK);
                                    break;
                                case 'b':
                                    piece = new Piece(Type.BISHOP, Color.BLACK);
                                    break;
                                case 'q':
                                    piece = new Piece(Type.QUEEN, Color.BLACK);
                                    break;
                                case 'k':
                                    piece = new Piece(Type.KING, Color.BLACK);
                                    break;
                                case 'p':
                                    piece = new Piece(Type.PAWN, Color.BLACK);
                                    break;
                            }
                        }
                        board.setPiece(piece, new Field(Line.values()[lineCount],Row.values()[rowCount]));
                        rowCount++;
                    }

                }
                lineCount--;
            }
        }
        return board;
    }

    /**
     * Converts the given string to a move object
     * @param input the string to be converted
     * @return the created move object
     */
    public static Move convertStringToMove(String input){
        input = input.toLowerCase();

        Move move = null;
        if(input.length() == 4){//move
            move = new Move(new Field(convertIntToLine(input.charAt(1) - '0'),convertCharToRow(input.charAt(0))), new Field(convertIntToLine(input.charAt(3) - '0'),convertCharToRow(input.charAt(2))));
        }else{//promotion length 5
            Type type = null;
            switch(input.charAt(4)){
                case 'q':
                    type = Type.QUEEN;
                    break;
                case 'b':
                    type = Type.BISHOP;
                    break;
                case 'n':
                    type = Type.KNIGHT;
                    break;
                case 'r':
                    type = Type.ROOK;
                    break;
            }
            move = new Move(new Field(convertIntToLine(input.charAt(1) - '0'),convertCharToRow(input.charAt(0))), new Field(convertIntToLine(input.charAt(3) - '0'),convertCharToRow(input.charAt(2))), type);
        }

        return move;
    }

    /**
     * Converts a move object to a string
     * @param input the move object to be converted
     * @return the string created
     */
    public static String convertMoveToString(Move input){
        String move = "";
        Field from = input.getFrom();
        Field to = input.getTo();

        move += convertRowToChar(from.getRow());
        move += convertLineToInt(from.getLine());
        move += convertRowToChar(to.getRow());
        move += convertLineToInt(to.getLine());

        if(input.getChangeTo() != null){
            switch (input.getChangeTo()){
                case QUEEN -> move += 'q';
                case BISHOP -> move += 'b';
                case KNIGHT -> move += 'n';
                case ROOK -> move += 'r';
            }
        }

        return move;
    }

    private static Line convertIntToLine(int line){
        return Line.values()[line-1];
    }

    private static int convertLineToInt(Line line){
        return line.ordinal() + 1;
    }

    private static Row convertCharToRow(char row){
        return switch (row) {
            case 'a' -> Row.A;
            case 'b' -> Row.B;
            case 'c' -> Row.C;
            case 'd' -> Row.D;
            case 'e' -> Row.E;
            case 'f' -> Row.F;
            case 'g' -> Row.G;
            default -> Row.H;
        };
    }

    private static char convertRowToChar(Row row){
        return switch (row) {
            case A -> 'a';
            case B -> 'b';
            case C -> 'c';
            case D -> 'd';
            case E -> 'e';
            case F -> 'f';
            case G -> 'g';
            case H -> 'h';
        };
    }
}
