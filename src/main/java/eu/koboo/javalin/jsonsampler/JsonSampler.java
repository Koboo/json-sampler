package eu.koboo.javalin.jsonsampler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.koboo.javalin.jsonsampler.property.PropertyLoader;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.json.JavalinGson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class JsonSampler {

    Gson gson;
    File endpointsDirectory;
    Javalin javalin;

    public JsonSampler() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("HH:mm:ss dd-mm-yyyy")
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .create();

        endpointsDirectory = new File("endpoints/");
        endpointsDirectory.mkdirs();

        javalin = Javalin.create(javalinConfig -> {
            javalinConfig.showJavalinBanner = false;
            javalinConfig.routing.ignoreTrailingSlashes = true;
            javalinConfig.routing.treatMultipleSlashesAsSingleSlash = true;
            javalinConfig.routing.caseInsensitiveRoutes = true;
            javalinConfig.jsonMapper(new JavalinGson());

            javalinConfig.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "endpoints/";
                staticFileConfig.location = Location.EXTERNAL;
                staticFileConfig.precompress = false;
            });
        });
    }

    protected void start() throws Exception {
        javalin.after(new JsonHandler(this));
        javalin.start(
                PropertyLoader.get(MyProperty.SERVER_HOST),
                PropertyLoader.get(MyProperty.SERVER_PORT)
        );
    }

    protected void stop() throws Exception {
        if(javalin != null) {
            javalin.stop();
        }
    }
}
