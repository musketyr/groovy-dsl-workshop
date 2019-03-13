package cz.orany.yuml.model;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

public interface Diagram {

    static Diagram create(Consumer<DiagramDefinition> diagramConsumer) {
        return DefaultDiagram.create(diagramConsumer);
    }

    Collection<Note> getNotes();

    Collection<Type> getTypes();

    Collection<Relationship> getRelationships();

    Map<String, Object> getMetadata();

}
