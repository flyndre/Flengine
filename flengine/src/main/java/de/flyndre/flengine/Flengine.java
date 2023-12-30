package de.flyndre.flengine;

import de.flyndre.flengine.converter.RequestHandler;
import de.flyndre.flengine.logging.LogChannelManager;

/**
 * Flengine chess engine by Team Flyndre @ DHBW Stuttgart Campus Horb
 * @author Team Flyndre
 * @see <a href="https://github.com/L4kiLuk/Flengine">Project Repository</a>
 */
public class Flengine {

    static {
        LogChannelManager.setup();
    }

    public static void main(String[] args) {
        new RequestHandler().startUp();
    }
}