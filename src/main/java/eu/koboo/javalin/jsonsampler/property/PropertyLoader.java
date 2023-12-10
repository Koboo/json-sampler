package eu.koboo.javalin.jsonsampler.property;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertyLoader {

    private static final Set<VarProperty<?>> PROPERTY_REGISTRY_SET = new LinkedHashSet<>();
    private static final Properties BOOT_PROPERTIES_INSTANCE = new Properties();

    private static String propertiesFileName = "boot.properties";
    private static boolean throwUnknownKeys = false;

    public static void propertiesFileName(String value) {
        propertiesFileName = value;
    }

    public static void throwErrorUnknownProperties(boolean value) {
        throwUnknownKeys = value;
    }

    public static void load(Class<?> propertyHolderClass) {
        for (Field declaredField : propertyHolderClass.getDeclaredFields()) {
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            if (!Modifier.isPublic(declaredField.getModifiers())) {
                continue;
            }
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                continue;
            }
            Class<?> fieldType = declaredField.getType();
            if (!VarProperty.class.isAssignableFrom(fieldType)) {
                continue;
            }
            try {
                VarProperty<?> propertyInstance = (VarProperty<?>) declaredField.get(null);
                PROPERTY_REGISTRY_SET.add(propertyInstance);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't read field=" + declaredField.getName() + " of class=" + propertyHolderClass.getName());
            }
        }

        for (VarProperty<?> property : PROPERTY_REGISTRY_SET) {
            BOOT_PROPERTIES_INSTANCE.setProperty(property.getPropertyKey(), String.valueOf(property.getDefaultValue()));
        }
    }

    public static <T> T get(VarProperty<T> property) {
        String value = BOOT_PROPERTIES_INSTANCE.getProperty(property.getPropertyKey());
        return property.getMapper().apply(value);
    }

    public static void appendResource() {
        InputStream stream = PropertyLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
        if (stream == null) {
            return;
        }
        appendStreamProperties(stream);
    }

    public static void appendFile() {
        try {
            File propertyFile = new File(propertiesFileName);
            if (!propertyFile.exists()) {
                return;
            }
            FileInputStream stream = new FileInputStream(propertyFile);
            appendStreamProperties(stream);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file storage properties: ", e);
        }
    }

    public static void appendArgs(String[] arguments) {
        for (String argument : arguments) {

            if (argument.startsWith("-")) {
                argument = argument.replace("-", "");
            }

            String[] split = argument.split("=");
            String key = split[0];

            String value = null;
            if (split.length == 1) {
                value = "true";
            }
            if (split.length == 2) {
                value = split[1];
            }

            if (value == null) {
                continue;
            }
            BOOT_PROPERTIES_INSTANCE.setProperty(key, value);
        }
    }

    public static void appendStreamProperties(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            BOOT_PROPERTIES_INSTANCE.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load properties from stream \"" + stream.getClass().getName() + "\":", e);
        }
        validateProperties();
    }

    private static void validateProperties() {
        for (Object keyObject : BOOT_PROPERTIES_INSTANCE.keySet()) {
            String key = String.valueOf(keyObject);
            if (isPropertyKey(key)) {
                continue;
            }
            if (throwUnknownKeys) {
                throw new IllegalArgumentException("Found unknown property key \"" + key + "\"");
            }
        }
    }

    private static boolean isPropertyKey(String key) {
        for (VarProperty<?> property : PROPERTY_REGISTRY_SET) {
            if (!property.getPropertyKey().equals(key)) {
                continue;
            }
            return true;
        }
        return false;
    }
}
