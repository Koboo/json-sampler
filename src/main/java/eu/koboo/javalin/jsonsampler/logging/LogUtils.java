package eu.koboo.javalin.jsonsampler.logging;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtils {

    public String stretch(String message, int length) {
        if (message.length() < length)
            for (int i = message.length(); i < length; i++) {
                message = "." + message;
            }
        else
            for (int i = message.length(); i > length; i--) {
                message = message.substring(1);
            }
        return message;
    }
}
