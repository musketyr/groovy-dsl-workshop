package cz.orany.yuml.export;

import cz.orany.yuml.model.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class YumlDiagramPrinter implements DiagramPrinter {

    public String print(Diagram diagram) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        for (Note note : diagram.getNotes()) {
            printWriter.println(print(note));
        }

        Map<String, Type> orphanTypes = diagram.getTypes().stream().collect(toMap(Type::getName, identity()));

        for (Relationship relationship : diagram.getRelationships()) {
            orphanTypes.remove(relationship.getSource().getName());
            orphanTypes.remove(relationship.getDestination().getName());

            printWriter.println(print(diagram, relationship));
        }

        for (Type type : orphanTypes.values()) {
            printWriter.println(print(diagram, type));
        }

        return stringWriter.toString();
    }

    // tag::type[]
    private String print(Diagram diagram, Type type) {
        Map<String, String> properties = PropertiesDiagramHelper.getProperties(diagram, type);

        if (properties.isEmpty()) {
            return String.format("[%s]", type.getName());
        }

        String propertiesFormatted = properties
                .entrySet()
                .stream()
                .map((e) -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.joining(";"));

        return String.format(
                "[%s|%s]",
                type.getName(),
                propertiesFormatted
        );
    }
    // end::type[]

    private String print(Diagram d, Relationship r) {
        switch (r.getType()) {
            case ASSOCIATION:
                return String.format("%s%s%s-%s>%s",
                    print(d, r.getSource()),
                    r.isBidirectional() ? "<" : "",
                    cardinalityAndTitle(r.getSourceCardinality(), r.getSourceTitle()),
                    cardinalityAndTitle(r.getDestinationCardinality(), r.getDestinationTitle()),
                    print(d, r.getDestination())
                );
            case AGGREGATION:
                return String.format("%s<>%s-%s>%s",
                    print(d, r.getSource()),
                    cardinalityAndTitle(r.getSourceCardinality(), r.getSourceTitle()),
                    cardinalityAndTitle(r.getDestinationCardinality(), r.getDestinationTitle()),
                    print(d, r.getDestination())
                );
            case COMPOSITION:
                return String.format("%s++%s-%s>%s",
                    print(d, r.getSource()),
                    cardinalityAndTitle(r.getSourceCardinality(), r.getSourceTitle()),
                    cardinalityAndTitle(r.getDestinationCardinality(), r.getDestinationTitle()),
                    print(d, r.getDestination())
                );
            case INHERITANCE:
                return String.format("%s^%s", print(d, r.getDestination()), print(d, r.getSource()));
            default:
                throw new IllegalArgumentException("Unknown type of relationship: " + r.getType());
        }
    }

    private String print(Note note) {
        if (note.getColor() != null) {
            return String.format("[note: %s{bg:%s}]", note.getText(), note.getColor());
        }

        return String.format("[note: %s]", note.getText());
    }

    private static String cardinalityAndTitle(String cardinality, String title) {
        if (cardinality != null) {
            return title != null ? (title + " " + cardinality) : cardinality;
        }

        return title != null ? title : "";
    }
}
