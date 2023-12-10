package eu.koboo.javalin.jsonsampler.property.types;

import eu.koboo.javalin.jsonsampler.property.VarProperty;

public class ShortProperty extends VarProperty<Short> {
    public ShortProperty(String propertyKey, Short defaultValue) {
        super(propertyKey, defaultValue, Short.class, Short::parseShort);
    }
}