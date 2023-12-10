package eu.koboo.javalin.jsonsampler;

import eu.koboo.javalin.jsonsampler.property.PropertyLoader;
import io.javalin.Javalin;
import io.javalin.json.JavalinGson;

public class JsonSampler {

    Javalin javalin;

    public JsonSampler() {

        this.javalin = Javalin.create(javalinConfig -> {
            javalinConfig.showJavalinBanner = false;
            javalinConfig.routing.ignoreTrailingSlashes = true;
            javalinConfig.routing.treatMultipleSlashesAsSingleSlash = true;
            javalinConfig.routing.caseInsensitiveRoutes = true;
            javalinConfig.jsonMapper(new JavalinGson());
        });
    }

    protected void start() throws Exception {
        this.javalin.start(
                PropertyLoader.get(MyProperty.SERVER_HOST),
                PropertyLoader.get(MyProperty.SERVER_PORT)
        );
    }

    protected void stop() throws Exception {
        this.javalin.stop();
    }
}
