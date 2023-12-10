package eu.koboo.javalin.jsonsampler.property.types;

import eu.koboo.javalin.jsonsampler.property.VarProperty;

public class LongProperty extends VarProperty<Long> {
    public LongProperty(String propertyKey, Long defaultValue) {
        super(propertyKey, defaultValue, Long.class, Long::parseLong);
    }
}