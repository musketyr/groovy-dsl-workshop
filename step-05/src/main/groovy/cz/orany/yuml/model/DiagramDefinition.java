package cz.orany.yuml.model;

import java.util.function.Consumer;
import java.util.function.Function;

public interface DiagramDefinition {

    default Note note(String text) {
        return note(text, null);
    }

    Note note(String text, String color);

    TypeDefinition type(String name);

    default TypeDefinition type(String name, Consumer<TypeDefinition> configuration) {
        TypeDefinition type = type(name);
        configuration.accept(type);
        return type;
    }

    default Relationship association(String source, String destination, Consumer<RelationshipDefinition> configuration) {
        return relationship(source, RelationshipType.ASSOCIATION, destination, configuration);
    }

    default Relationship aggregation(String source, String destination, Consumer<RelationshipDefinition> configuration) {
        return relationship(source, RelationshipType.AGGREGATION, destination, configuration);
    }

    default Relationship inheritance(String source, String destination, Consumer<RelationshipDefinition> configuration) {
        return relationship(source, RelationshipType.INHERITANCE, destination, configuration);
    }

    default Relationship composition(String source, String destination, Consumer<RelationshipDefinition> configuration) {
        return relationship(source, RelationshipType.COMPOSITION, destination, configuration);
    }

    Relationship relationship(String source, RelationshipType type, String destination, Consumer<RelationshipDefinition> configuration);

    <H extends DiagramHelper, R> R configure(Class<H> helper, Function<H, R> configurationOrQuery);
}
