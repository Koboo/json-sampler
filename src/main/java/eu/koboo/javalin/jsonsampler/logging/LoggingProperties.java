package eu.koboo.javalin.jsonsampler.logging;

import lombok.extern.java.Log;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

@Log
public class LoggingProperties {

    private static final String FILE_NAME = "logging.properties";

    public static void read() {
        boolean fromResource = false;
        boolean exportedDefaultProperties = false;
        File fileOnHardDrive = new File(FILE_NAME);
        try {
            if (!fileOnHardDrive.exists()) {
                Properties sampleProps = new Properties();
                InputStream resourceStream = LoggingProperties.class.getResourceAsStream("/" + FILE_NAME);
                if (resourceStream != null) {
                    // Read properties from resource
                    sampleProps.load(resourceStream);
                    fromResource = true;
                } else {
                    // Create default properties
                    exportedDefaultProperties = true;
                    // Append Handlers
                    sampleProps.setProperty("handlers", "java.util.logging.FileHandler, java.util.logging.ConsoleHandler");
                    // Configure FileHandler
                    sampleProps.setProperty("java.util.logging.FileHandler.pattern", "console.log");
                    sampleProps.setProperty("java.util.logging.FileHandler.level", "ALL");
                    sampleProps.setProperty("java.util.logging.FileHandler.formatter", LogFormatter.class.getName());
                    sampleProps.setProperty("java.util.logging.ConsoleHandler.level", "ALL");
                    // Configure ConsoleHandler
                    sampleProps.setProperty("java.util.logging.ConsoleHandler.formatter", "ALL");
                    sampleProps.setProperty("java.util.logging.ConsoleHandler.formatter", LogFormatter.class.getName());
                }
                fileOnHardDrive.createNewFile();
                PrintWriter writer = new PrintWriter(new FileOutputStream(fileOnHardDrive));
                sampleProps.store(writer, "Change to your custom needs for better logging.");
                writer.close();
            }
            try (InputStream inputStream = new FileInputStream(fileOnHardDrive)) {
                LogManager.getLogManager().readConfiguration(inputStream);
            }
            if (exportedDefaultProperties) {
                log.info("Exported default logging properties.");
            }
            log.info("Using logging properties " + (fromResource ? "from resources" : "from file-system") + "..");
        } catch (IOException e) {
            log.log(Level.ALL, "Couldn't load " + FILE_NAME + "!", e);
        }
    }

}
