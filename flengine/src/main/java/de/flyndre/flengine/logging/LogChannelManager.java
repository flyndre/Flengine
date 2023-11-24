package de.flyndre.flengine.logging;

import de.flyndre.flengine.logging.channel.ConsoleLogChannel;
import de.flyndre.flengine.logging.channel.FileLogChannel;
import de.flyndre.flengine.logging.channel.LogChannel;
import de.flyndre.flengine.logging.channel.UciLogChannel;

import java.util.Map;
import java.util.Objects;
import java.util.logging.*;

public abstract class LogChannelManager {

    private static final String CONFIG_SCOPE = "de.flyndre.flengine";
    private static final String LOG_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s - %5$s%6$s";
    private static final String DATE_FORMAT = "yyyyMMdd-HHmmss-SSS";
    private static final String LOGFILE_PREFIX = "./flengine-";

    private static final Map<LogChannelType, LogChannel> channels = Map.of(
            LogChannelType.UCI, new UciLogChannel(CONFIG_SCOPE, "info string " + LOG_FORMAT),
            LogChannelType.FILE, new FileLogChannel(CONFIG_SCOPE, LOG_FORMAT + "%n", DATE_FORMAT, LOGFILE_PREFIX),
            LogChannelType.CONSOLE, new ConsoleLogChannel(CONFIG_SCOPE, LOG_FORMAT + "%n")
    );

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

    public static void setOpen(LogChannelType type, boolean open) {
        channels.get(type).setOpen(open);
    }

    public static boolean isOpen(LogChannelType type) {
        return channels.get(type).isOpen();
    }

}
