package eu.koboo.javalin.jsonsampler;

import eu.koboo.javalin.jsonsampler.logging.LoggingProperties;
import eu.koboo.javalin.jsonsampler.property.PropertyLoader;

public class Bootstrap {

    public static void main(String[] args) {
        LoggingProperties.read();

        PropertyLoader.load(MyProperty.class);
        PropertyLoader.throwErrorUnknownProperties(true);

        PropertyLoader.appendFile();
        PropertyLoader.appendResource();
        PropertyLoader.appendArgs(args);

        try {
            JsonSampler application = new JsonSampler();
            Thread shutdownThread = new Thread(() -> {
                try {
                    application.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "ShutdownThread");
            Runtime.getRuntime().addShutdownHook(shutdownThread);
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
