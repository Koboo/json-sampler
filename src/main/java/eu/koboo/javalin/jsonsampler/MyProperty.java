package eu.koboo.javalin.jsonsampler;

import eu.koboo.javalin.jsonsampler.property.VarProperty;
import eu.koboo.javalin.jsonsampler.property.types.IntProperty;
import eu.koboo.javalin.jsonsampler.property.types.StringProperty;


public class MyProperty {

    public static final VarProperty<String> SERVER_HOST = new StringProperty("server.host", "localhost");
    public static final VarProperty<Integer> SERVER_PORT = new IntProperty("server.port", 8080);
}
