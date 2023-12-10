package eu.koboo.javalin.jsonsampler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import eu.koboo.javalin.jsonsampler.logging.LogUtils;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log
public class JsonHandler implements Handler {

    JsonSampler jsonSampler;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        log.info("=================================================");
        int stretch = 20;
        log.info(LogUtils.stretch("ip: ", stretch) + getIp(ctx));
        log.info(LogUtils.stretch("url: ", stretch) + ctx.fullUrl());
        log.info(LogUtils.stretch("path: ", stretch) + ctx.path());
        log.info(LogUtils.stretch("method: ", stretch) + ctx.method().name());
        String contentType = ctx.contentType();
        if(contentType != null && !contentType.isEmpty()) {
            log.info(LogUtils.stretch("content: ", stretch) + contentType);
        }
        log.info(LogUtils.stretch("user-agent: ", stretch) + ctx.userAgent());
        log.info(LogUtils.stretch("query params: ", stretch) + ctx.queryString());
        //log.info(LogUtils.stretch("header: ", stretch) + ctx.headerMap());

        if (ctx.bodyInputStream().available() > 0) {
            log.info(LogUtils.stretch("body: ", stretch) + ctx.body());
        }
        log.info("=================================================");

    }

    private String getIp(Context ctx) {
        String forwardedIp = ctx.header("X-Forwarded-For");
        if(forwardedIp != null && !forwardedIp.isEmpty()) {
            return forwardedIp;
        }
        forwardedIp = ctx.header("X-Real-IP");
        if(forwardedIp != null && !forwardedIp.isEmpty()) {
            return forwardedIp;
        }
        forwardedIp = ctx.header("CF-Connecting-IP");
        if(forwardedIp != null && !forwardedIp.isEmpty()) {
            return forwardedIp;
        }
        return ctx.ip();
    }
}
