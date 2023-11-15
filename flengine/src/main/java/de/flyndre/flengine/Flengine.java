package de.flyndre.flengine;

import de.flyndre.flengine.converter.RequestHandler;
import de.flyndre.flengine.util.UciHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Flengine chess engine by Team Flyndre @ DHBW Stuttgart Campus Horb
 * @author Team Flyndre
 * @see <a href="https://github.com/L4kiLuk/Flengine">Project Repository</a>
 */
public class Flengine {

    static {
        // Setup logger configuration
        var loggingFormatKey = "java.util.logging.SimpleFormatter.format";
        var loggingFormat = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s - %5$s%6$s";
        var loggingType = System.getProperty("de.flyndre.flengine.logging");

        switch (loggingType) {
            case "uci" -> {
                LogManager.getLogManager().reset();
                System.setProperty(loggingFormatKey, "info string " + loggingFormat);
                Logger.getLogger("de.flyndre.flengine").addHandler(new UciHandler());
            }
            case "file" -> {
                LogManager.getLogManager().reset();
                System.setProperty(loggingFormatKey, loggingFormat + "%n");
                var timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());
                try {
                    var handler = new FileHandler(new File("./flengine-" + timestamp + ".log").getAbsolutePath());
                    handler.setFormatter(new SimpleFormatter());
                    Logger.getLogger("de.flyndre.flengine").addHandler(handler);
                } catch (IOException e) {
                    //
                }
            }
            case "console" -> System.setProperty(loggingFormatKey, loggingFormat);
            default -> LogManager.getLogManager().reset();
        }
    }

    public static void main(String[] args) {
        var handler = new RequestHandler();
        handler.startUp();
    }
}