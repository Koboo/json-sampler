package eu.koboo.javalin.jsonsampler.property.types;

import eu.koboo.javalin.jsonsampler.property.VarProperty;

public class BooleanProperty extends VarProperty<Boolean> {
    public BooleanProperty(String propertyKey, Boolean defaultValue) {
        super(propertyKey, defaultValue, Boolean.class, Boolean::parseBoolean);
    }
}