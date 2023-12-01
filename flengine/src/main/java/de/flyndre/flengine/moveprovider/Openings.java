package de.flyndre.flengine.moveprovider;

import de.flyndre.flengine.datamodel.Options;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import de.flyndre.flengine.converter.Converter;
import de.flyndre.flengine.datamodel.Board;
import de.flyndre.flengine.datamodel.Move;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * An implementation of {@code MoveProvider} that uses the Lichess Opening Explorer
 * to find the best move in an opening situation.
 * @author David
 */
public class Openings implements MoveProvider {

    public static class OpeningResponse {
        public int white;
        public int draws;
        public int black;
        public List<OpeningMove> moves;
        public static class OpeningMove {
            public String uci;
            public int averageRating;
            public int white;
            public int draws;
            public int black;
        }
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Provides a list of recommended {@code Move}s for the given situation on the {@code Board}.
     * @param board The current board.
     * @return A list of recommended moves in this situation which may be empty if none were found.
     */
    @Override
    public List<Move> getRecommendedMoves(Board board, Options options) {
        var fenString = Converter.convertBoardToString(board);
        var request = new Request.Builder()
                .url("https://explorer.lichess.ovh/masters?fen=" + fenString + "&topGames=0")
                .build();
        logger.info("Requesting opening moves for: [" + fenString + "]");
        try (var response = new OkHttpClient().newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected response code: " + response);
            var openingData = JsonbBuilder.create().fromJson(response.body().string(), OpeningResponse.class);
            logger.info("Received: [" + openingData.moves.size() + " moves]");
            return openingData.moves.stream()
                    .map(m -> Converter.convertStringToMove(m.uci))
                    .map(m -> Converter.sanitizeMove(board, m))
                    .toList();
        } catch (IOException e) {
            logger.warning("Request failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
