package eu.koboo.javalin.jsonsampler.property.types;

import eu.koboo.javalin.jsonsampler.property.VarProperty;

public class IntProperty extends VarProperty<Integer> {
    public IntProperty(String propertyKey, Integer defaultValue) {
        super(propertyKey, defaultValue, Integer.class, Integer::parseInt);
    }
}