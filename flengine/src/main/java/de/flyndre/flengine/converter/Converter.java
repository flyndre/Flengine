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
                            piece = switch (Character.toLowerCase(c)) {//white
                                case 'r' -> new Piece(Type.ROOK, Color.WHITE);
                                case 'n' -> new Piece(Type.KNIGHT, Color.WHITE);
                                case 'b' -> new Piece(Type.BISHOP, Color.WHITE);
                                case 'q' -> new Piece(Type.QUEEN, Color.WHITE);
                                case 'k' -> new Piece(Type.KING, Color.WHITE);
                                case 'p' -> new Piece(Type.PAWN, Color.WHITE);
                                default -> piece;
                            };
                        }else{
                            piece = switch (Character.toLowerCase(c)) {//black
                                case 'r' -> new Piece(Type.ROOK, Color.BLACK);
                                case 'n' -> new Piece(Type.KNIGHT, Color.BLACK);
                                case 'b' -> new Piece(Type.BISHOP, Color.BLACK);
                                case 'q' -> new Piece(Type.QUEEN, Color.BLACK);
                                case 'k' -> new Piece(Type.KING, Color.BLACK);
                                case 'p' -> new Piece(Type.PAWN, Color.BLACK);
                                default -> piece;
                            };
                        }
                        board.setPiece(piece, new Field(Line.values()[lineCount],Row.values()[rowCount]));
                        rowCount++;
                    }

                }
                lineCount--;
            }

            //decode next move color
            if(split[1].equals("w")){
                board.setNextColor(Color.WHITE);
            }else{
                board.setNextColor(Color.BLACK);
            }

            //decode castling
            String castling = split[2];

            board.setWhiteShortCastling(false);
            board.setWhiteLongCastling(false);
            board.setBlackShortCastling(false);
            board.setBlackLongCastling(false);

            for(int i = 0; i < castling.length(); i++){
                switch(castling.charAt(i)){
                    case 'K' -> board.setWhiteShortCastling(true);
                    case 'Q' -> board.setWhiteLongCastling(true);
                    case 'k' -> board.setBlackShortCastling(true);
                    case 'q' -> board.setBlackLongCastling(true);
                }
            }

            //decode en passant

            //decode half moves

            //decode move number
        }
        return board;
    }

    /**
     * converts the given board object into a fen string
     * @param board the board object to be converted
     * @return the fen string
     */
    public static String convertBoardToString(Board board){
        String fen = "";
        int emptyFieldCounter = 0;
        boolean isCastlingPossible = false;

        //get pieces
        for(int a = 7; a >= 0; a--){
            for(int b = 0; b < 8; b++){
                Piece currentPiece = board.getPiece(new Field(Line.values()[a], Row.values()[b]));

                if(currentPiece != null){//check for empty field
                    String currentPieceFenRepresentation = "";

                    switch (currentPiece.getTypeOfFigure()){//get piece
                        case ROOK -> currentPieceFenRepresentation = "r";
                        case KNIGHT -> currentPieceFenRepresentation = "n";
                        case BISHOP -> currentPieceFenRepresentation = "b";
                        case QUEEN -> currentPieceFenRepresentation = "q";
                        case KING -> currentPieceFenRepresentation = "k";
                        case PAWN -> currentPieceFenRepresentation = "p";
                    }
                    if(currentPiece.getColor() == Color.WHITE){//set piece color
                        currentPieceFenRepresentation = currentPieceFenRepresentation.toUpperCase();
                    }

                    //add piece to fen string
                    if(emptyFieldCounter > 0){
                        fen += emptyFieldCounter;
                        fen += currentPieceFenRepresentation;

                        emptyFieldCounter = 0;
                    }else{
                        fen += currentPieceFenRepresentation;
                    }
                }else{
                    emptyFieldCounter++;
                }
            }
            //after row is finished
            if(emptyFieldCounter > 0){
                fen += emptyFieldCounter;
                emptyFieldCounter = 0;
            }

            fen += "/";
        }

        //get next move color
        if(board.getNextColor() == Color.WHITE){
            fen += " w";
        }else{
            fen += " b";
        }

        //get castling information
        fen += " ";

        if(board.getWhiteShortCastling()){
            isCastlingPossible = true;
            fen += "K";
        }

        if(board.getWhiteLongCastling()){
            isCastlingPossible = true;
            fen += "Q";
        }

        if(board.getBlackShortCastling()){
            isCastlingPossible = true;
            fen += "k";
        }

        if(board.getBlackLongCastling()){
            isCastlingPossible = true;
            fen += "q";
        }
        //check whether no castling is possible
        if(!isCastlingPossible){
            fen += "-";
        }

        //get en passant information
        //not implemented yet, hardcoded dummy value
        fen += " -";

        //get half moves information
        //not implemented yet, hardcoded dummy value
        fen += " 0";

        //get number of move
        //not implemented yet, hardcoded dummy value
        fen += " 1";

        return fen;
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

        if(input.getPromoteTo() != null){
            switch (input.getPromoteTo()){
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
