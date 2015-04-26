package main.game.util;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.game.reference.Directories;

public final class Logger {

    public static enum LogLevel {
        DEBUG, INFO, WARN
    }

    private static final DateFormat EXTENDED_DATE = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
    private static final List<String> LOG = new ArrayList<String>();
    private static final DateFormat SIMPLE_DATE = new SimpleDateFormat("HH:mm:ss");

    public static void debug(String msg) {
        log(LogLevel.DEBUG, msg);
    }

    public static void debug(String msg, Object... objs) {
        log(LogLevel.DEBUG, msg, objs);
    }

    public static void info(String msg) {
        log(LogLevel.INFO, msg);
    }

    public static void info(String msg, Object... objs) {
        log(LogLevel.INFO, msg, objs);
    }

    public static void log(LogLevel level, String msg, Object... objs) {
        if (level != null && msg != null && !msg.equalsIgnoreCase("")) {
            synchronized (SIMPLE_DATE) {
                String out1 = String.format("[%s] [%s]: %s", SIMPLE_DATE.format(new Date()), level.toString(), msg);
                LOG.add(out1);
                synchronized (System.out) {
                    System.out.println(out1);
                }
            }
            if (objs != null && objs.length > 0) {
                int i = 1;
                for (Object o : objs) {
                    String oStr = o != null ? o.toString() : "null";
                    if (oStr == null) {
                        oStr = "null";
                    }
                    synchronized (SIMPLE_DATE) {
                        String out2 = String.format("[%s] [%s]: %d) %s", SIMPLE_DATE.format(new Date()), level.toString(), i++, oStr);
                        LOG.add(out2);
                        synchronized (System.out) {
                            System.out.println(out2);
                        }
                    }
                }
            }
        }
    }

    public static void logThrowable(String msg, Throwable t) {
        if (t != null) {

            List<String> stackTrace = new ArrayList<String>();

            stackTrace.add(t.toString());
            StackTraceElement[] trace = t.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                stackTrace.add("\tat " + traceElement);
            }

            for (Throwable se : t.getSuppressed()) {
                if (se != null) {
                    logThrowable("Supressed", se);
                }
            }

            Throwable cause = t.getCause();
            if (cause != null) {
                logThrowable("Caused by", cause);
            }

            warn(msg + ": " + t.toString(), stackTrace.toArray());
        } else {
            warn(msg + ": " + t);
        }
    }

    public static void saveLog() {
        synchronized (EXTENDED_DATE) {
            try {
                Files.write(new File(Directories.getLogDir(), "log_" + EXTENDED_DATE.format(new Date()) + ".txt").toPath(), LOG, Charset.defaultCharset());
            } catch (Throwable t) {
                Logger.logThrowable("Unable to save log", t);
            }
        }
    }

    public static void warn(String msg) {
        log(LogLevel.WARN, msg);
    }

    public static void warn(String msg, Object... objs) {
        log(LogLevel.WARN, msg, objs);
    }

    private Logger() {
        // NO-OP
    }

}
