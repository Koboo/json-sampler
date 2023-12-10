package eu.koboo.javalin.jsonsampler.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String[] sourceSplit = record.getSourceClassName().split("\\.");
        String source = LogUtils.stretch(record.getSourceClassName(), 30);
        String timeStamp = SDF.format(new Date(record.getMillis()));
        String message = record.getMessage();
        if (record.getThrown() != null) {
            message += convertToString(record.getThrown());
        }
        return "[" + record.getLevel() + "] [" + timeStamp + "] [" + record.getSourceClassName() + "] " + message + System.lineSeparator();
    }

    public String convertToString(Throwable throwable) {
        try (StringWriter sw = new StringWriter()) {
            throwable.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't print exception: ", e);
        }
    }


}
