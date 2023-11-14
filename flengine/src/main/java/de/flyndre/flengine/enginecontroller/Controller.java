package de.flyndre.flengine.enginecontroller;

import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Field;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.datamodel.Options;
import de.flyndre.flengine.datamodel.enums.Line;
import de.flyndre.flengine.datamodel.enums.Row;
import de.flyndre.flengine.datamodel.enums.Type;
import de.flyndre.flengine.moveprovider.Endgame;
import de.flyndre.flengine.moveprovider.MoveProvider;
import de.flyndre.flengine.moveprovider.Openings;
import de.flyndre.flengine.moveprovider.Stub;
import de.flyndre.flengine.rules.Rule;
import de.flyndre.flengine.util.FileLogger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * The centerpiece of Flengine.
 * Orchestrates the move-determination-process by retrieving moves from a list of {@code MoveProviders}.
 * @author David
 */
public class Controller {

    private static final Logger logger = FileLogger.getLogger("Controller");

    /**
     * This number is used to tweak the difficulty of the engine.
     * A lower number makes it less difficult, a higher one more difficult.
     * The value 1 gives all moves the same probability.
     */
    private static final double DIFFICULTY = 8;

    private static final List<MoveProvider> moveProviderHierarchy = Arrays.asList(
            new Openings(),
            new Endgame(),
            new Stub(new Rule())
    );

    /**
     * Determines the best move based on the given {@code Board} and {@code Options}.
     * @param board The current board.
     * @param options An object containing options about the move obtainment process.
     * @return A single {@code Move}, which the engine determined as best possible move or {@code null}, if no move was found.
     */
    public static Move giveMove(Board board, Options options) {
        for (var moveProvider : moveProviderHierarchy) {
            logger.info("Requesting moves from: [" + moveProvider.getClass().getName() + "]");
            var moves = moveProvider.getRecommendedMoves(board);
            if (moves != null && !moves.isEmpty()){
                logger.info("Received: [" + moves.size() + " moves]");
                Move bestMove =  moves.get(
                        (int) Math.floor(
                                // Squaring the random number to control which items are more probable:
                                // (1) A higher exponent makes the front items of the list more probable.
                                // (2) Because the front moves should be the better ones, this makes
                                //     the engine more difficult.
                                //
                                // (*) The modulo 1 is needed to prevent values greater than 1 if
                                //     the difficulty is less than 0.
                                Math.pow(Math.random(), DIFFICULTY) % 1 * moves.size()
                    )
                );

                // convert lichess castle move to uci castle move
                if (board.getPiece(bestMove.getFrom()).getTypeOfFigure().equals(Type.KING) &&
                        bestMove.getFrom().getRow().equals(Row.E) &&
                        (bestMove.getFrom().getLine().equals(Line.ONE) && bestMove.getTo().getLine().equals(Line.ONE) ||
                        bestMove.getFrom().getLine().equals(Line.EIGHT) && bestMove.getTo().getLine().equals(Line.EIGHT)))
                {
                    if (bestMove.getTo().getRow().equals(Row.H)) bestMove.setTo(new Field(bestMove.getTo().getLine(), Row.G));
                    else if (bestMove.getTo().getRow().equals(Row.A)) bestMove.setTo(new Field(bestMove.getTo().getLine(), Row.C));
                }

                logger.info("Best move is " + bestMove + " by " + moveProvider.getClass().getName());
                return bestMove;
            }
        }
        logger.warning("No possible moves were found.");
        return null;
    }
}
