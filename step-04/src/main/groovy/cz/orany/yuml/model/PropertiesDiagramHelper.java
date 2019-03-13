package cz.orany.yuml.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesDiagramHelper implements DiagramHelper {

    private static final String METADATA_KEY
            = "cz.orany.yuml.properties.PropertiesDiagramHelper.typeToProperties";

    private final Map<String, Map<String, String>> typeToProperties                     // <1>
            = new LinkedHashMap<>();

    public PropertiesDiagramHelper addProperty(String owner, String type, String name) {// <2>
        Map<String, String> properties = typeToProperties.computeIfAbsent(
                owner, (key) -> new LinkedHashMap<>()
        );

        properties.put(name, type);

        return this;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Collections.singletonMap(METADATA_KEY, typeToProperties);                // <3>
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> getProperties(Diagram diagram, Type type) {       // <4>
        Map<String, Map<String, String>> typeToProperties = (Map<String, Map<String, String>>) diagram
                .getMetadata()
                .computeIfAbsent(METADATA_KEY, (key) -> new LinkedHashMap<>());
        return typeToProperties.computeIfAbsent(type.getName(), (key) -> new LinkedHashMap<>());
    }
}
