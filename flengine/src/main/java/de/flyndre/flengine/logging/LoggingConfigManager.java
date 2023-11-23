package de.flyndre.flengine.logging;

import de.flyndre.flengine.logging.config.ConsoleLoggingConfig;
import de.flyndre.flengine.logging.config.FileLoggingConfig;
import de.flyndre.flengine.logging.config.LoggingConfig;
import de.flyndre.flengine.logging.config.UciLoggingConfig;

import java.util.Map;
import java.util.Objects;
import java.util.logging.*;

public abstract class LoggingConfigManager {

    static {
        LogManager.getLogManager().reset();
    }

    private static final String CONFIG_SCOPE = "de.flyndre.flengine";
    private static final String LOG_FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s - %5$s%6$s";
    private static final String DATE_FORMAT = "yyyyMMdd-HHmmss-SSS";
    private static final String LOGFILE_PREFIX = "./flengine-";

    private static final Map<LoggingType, LoggingConfig> options = Map.of(
            LoggingType.UCI, new UciLoggingConfig(CONFIG_SCOPE, "info string " + LOG_FORMAT),
            LoggingType.FILE, new FileLoggingConfig(CONFIG_SCOPE, LOG_FORMAT + "%n", DATE_FORMAT, LOGFILE_PREFIX),
            LoggingType.CONSOLE, new ConsoleLoggingConfig(CONFIG_SCOPE, LOG_FORMAT + "%n")
    );

    public static void setup() {
        var packageName = LoggingConfigManager.class.getPackageName();
        for (var type : options.keySet()) {
            try {
                var active = System.getProperty(packageName + "." + type.name().toLowerCase());
                if (Objects.equals(active, "true"))
                    setActive(type, true);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void setActive(LoggingType type, boolean active) {
        options.get(type).setActive(active);
    }

    public static boolean isActive(LoggingType type) {
        return options.get(type).isActive();
    }

}
