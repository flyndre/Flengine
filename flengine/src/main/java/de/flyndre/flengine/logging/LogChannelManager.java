package de.flyndre.flengine.logging;

import de.flyndre.flengine.logging.channel.ConsoleLogChannel;
import de.flyndre.flengine.logging.channel.FileLogChannel;
import de.flyndre.flengine.logging.channel.LogChannel;
import de.flyndre.flengine.logging.channel.UciLogChannel;

import java.util.Map;
import java.util.Objects;
import java.util.logging.*;

/**
 * Manages a set of {@code LogManagers} mapped to a {@code LogChannelType} centrally.
 * Provides functionality to open and close specific channels programmatically or on startup by setting
 * their Java VM options.
 * @author David
 */
public abstract class LogChannelManager {

    private static final String CONFIG_SCOPE = "de.flyndre.flengine";
    private static final String LOG_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s - %5$s%6$s";
    private static final String DATE_FORMAT = "yyyyMMdd-HHmmss-SSS";
    private static final String LOGFILE_PREFIX = "./flengine-";
    private static final Logger LOGGER = Logger.getLogger(CONFIG_SCOPE);

    private static final Map<LogChannelType, LogChannel> channels = Map.of(
            LogChannelType.UCI, new UciLogChannel(LOGGER, "info string " + LOG_FORMAT),
            LogChannelType.FILE, new FileLogChannel(LOGGER, LOG_FORMAT + "%n", DATE_FORMAT, LOGFILE_PREFIX),
            LogChannelType.CONSOLE, new ConsoleLogChannel(LOGGER, LOG_FORMAT + "%n")
    );

    /**
     * Initializes the {@code LogChannelManager} based on Java VM options.
     * This should be triggered on program startup.
     */
    public static void setup() {
        LogManager.getLogManager().reset();
        var packageName = LogChannelManager.class.getPackageName();
        for (var type : channels.keySet()) {
            try {
                var open = System.getProperty(packageName + "." + type.name().toLowerCase());
                if (Objects.equals(open, "true"))
                    setOpen(type, true);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * Set whether the {@code LogChannel} of the given {@code LogChannelType} is open.
     * If already opened or closed, no action will be taken.
     * @param type the {@code LogChannelType} of the channel to be opened/closed
     * @param open the new <i>open</i> status
     */
    public static void setOpen(LogChannelType type, boolean open) {
        channels.get(type).setOpen(open);
    }

    /**
     * States whether the {@code LogChannel} of the given {@code LogChannelType} is currently open.
     * @param type the {@code LogChannelType} of the channel
     * @return the current <i>open</i> status
     */
    public static boolean isOpen(LogChannelType type) {
        return channels.get(type).isOpen();
    }

}
