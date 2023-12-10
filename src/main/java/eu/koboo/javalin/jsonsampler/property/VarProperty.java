package eu.koboo.javalin.jsonsampler.property;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Log
@ToString
@EqualsAndHashCode
public class VarProperty<T> {

    String propertyKey;
    Class<T> type;
    Object defaultValue;
    Function<String, T> mapper;

    public VarProperty(String propertyKey, T defaultValue, Class<T> type, Function<String, T> mapper) {
        this.propertyKey = propertyKey;
        this.defaultValue = defaultValue;
        this.type = type;
        this.mapper = mapper;
    }
}
