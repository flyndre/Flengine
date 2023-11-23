package de.flyndre.flengine;

import de.flyndre.flengine.converter.RequestHandler;
import de.flyndre.flengine.logging.LoggingConfigManager;

/**
 * Flengine chess engine by Team Flyndre @ DHBW Stuttgart Campus Horb
 * @author Team Flyndre
 * @see <a href="https://github.com/L4kiLuk/Flengine">Project Repository</a>
 */
public class Flengine {

    static {
        LoggingConfigManager.setup();
    }

    public static void main(String[] args) {
        var handler = new RequestHandler();
        handler.startUp();
    }
}