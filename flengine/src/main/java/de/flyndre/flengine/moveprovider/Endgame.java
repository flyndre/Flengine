package de.flyndre.flengine.moveprovider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import de.flyndre.flengine.util.FileLogger;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An implementation of {@code MoveProvider} that uses the Lichess Tablebase
 * to find the best move in an endgame situation.
 * @author David
 */
public class Endgame implements MoveProvider {

    public static class EndgameResponse {
        public boolean checkmate;
        public boolean stalemate;
        public boolean variantWin;
        public boolean variantLoss;
        public boolean insufficientMaterial;
        public EndgameCategory category;
        public List<EndgameMove> moves;

        public enum EndgameCategory {
            WIN,
            LOSS
        }

        public static class EndgameMove {
            public String uci;
            public boolean zeroing;
            public boolean checkmate;
            public boolean stalemate;
            public boolean variantWin;
            public boolean variantLoss;
            public boolean insufficientMaterial;
            public EndgameCategory category;
        }
    }

    private final Logger logger = FileLogger.getLogger("Endgame");

    /**
     * Provides a list of recommended {@code Move}s for the given situation on the {@code Board}.
     * @param board The current board.
     * @return A list of recommended moves in this situation which may be empty if none were found.
     */
    @Override
    public List<Move> getRecommendedMoves(Board board) {
        var pieceCount = board.pieceCount();
        if (board.pieceCount() > 7) {
            logger.info("The given board has more than 7 pieces left: [" + pieceCount + "]");
            return new ArrayList<>();
        }
        var fenString = Converter.convertBoardToString(board);
        var request = new Request.Builder()
                .url("https://tablebase.lichess.ovh/standard?fen=" + fenString)
                .build();
        logger.info("Requesting endgame moves for: [" + fenString + "]");
        try (var response = new OkHttpClient().newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected response code: " + response);
            var endgameData = JsonbBuilder.create().fromJson(response.body().string(), EndgameResponse.class);
            logger.info("Received: [" + endgameData.moves.size() + " moves]");
            return endgameData.moves.stream()
                    .map(m -> Converter.convertStringToMove(m.uci))
                    .map(m -> Converter.sanitizeMove(board, m))
                    .toList();
        } catch (IOException e) {
            logger.warning("Request failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
