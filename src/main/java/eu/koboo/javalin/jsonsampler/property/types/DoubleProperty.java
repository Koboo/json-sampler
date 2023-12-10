package eu.koboo.javalin.jsonsampler.property.types;

import eu.koboo.javalin.jsonsampler.property.VarProperty;

public class DoubleProperty extends VarProperty<Double> {
    public DoubleProperty(String propertyKey, Double defaultValue) {
        super(propertyKey, defaultValue, Double.class, Double::parseDouble);
    }
}