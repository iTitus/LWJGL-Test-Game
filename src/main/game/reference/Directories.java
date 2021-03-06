package main.game.reference;

import java.io.File;

public class Directories {

    private static String configDir;
    private static File configDirFile;
    private static String logDir;
    private static File logDirFile;
    private static String programDir;
    private static File programDirFile;

    public static File getConfigDir() {
        if (configDirFile == null) {
            configDirFile = new File(getConfigDirPath());
        }
        configDirFile.mkdirs();
        return configDirFile;
    }

    public static String getConfigDirPath() {
        if (configDir == null) {
            configDir = getProgramDirPath() + "config";
        }
        return configDir;
    }

    public static File getLogDir() {
        if (logDirFile == null) {
            logDirFile = new File(getLogDirPath());
        }
        logDirFile.mkdirs();
        return logDirFile;
    }

    public static String getLogDirPath() {
        if (logDir == null) {
            logDir = getProgramDirPath() + "logs";
        }
        return logDir;
    }

    public static File getProgramDir() {
        if (programDirFile == null) {
            programDirFile = new File(getProgramDirPath());
        }
        programDirFile.mkdirs();
        return programDirFile;
    }

    public static String getProgramDirPath() {
        if (programDir == null) {
            programDir = System.getProperty("user.home") + File.separatorChar + ".projectY" + File.separatorChar;
        }
        return programDir;
    }
}
