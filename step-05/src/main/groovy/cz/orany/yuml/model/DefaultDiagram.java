package cz.orany.yuml.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DefaultDiagram implements Diagram, DiagramDefinition {

    public static Diagram create(Consumer<DiagramDefinition> diagramConsumer) {
        DefaultDiagram diagram = new DefaultDiagram();
        diagramConsumer.accept(diagram);
        diagram.postprocess();
        return diagram;
    }

    private Collection<Note> notes = new LinkedHashSet<>();
    private Map<String, DefaultType> types = new LinkedHashMap<>();
    private Collection<DefaultRelationship> relationships = new LinkedHashSet<>();
    private Map<Class, DiagramHelper> helperMap = new LinkedHashMap<>();
    private Map<String, Object> metadata = new LinkedHashMap<>();

    @Override
    public Note note(String text, String color) {
        Note note = new DefaultNote(text, color);
        notes.add(note);
        return note;
    }

    @Override
    public DefaultType type(String name) {
        return types.computeIfAbsent(name, n -> new DefaultType(this, n));
    }

    @Override
    public DefaultRelationship relationship(String source, RelationshipType type, String destination, Consumer<RelationshipDefinition> configuration) {
        DefaultRelationship relationship = new DefaultRelationship(type(source), type, type(destination));
        configuration.accept(relationship);
        relationships.add(relationship);
        return relationship;
    }

    @Override
    public Collection<Note> getNotes() {
        return Collections.unmodifiableCollection(notes);
    }

    @Override
    public Collection<Type> getTypes() {
        return Collections.unmodifiableCollection(types.values());
    }

    @Override
    public Collection<Relationship> getRelationships() {
        return Collections.unmodifiableCollection(relationships);
    }

    @Override
    public <H extends DiagramHelper, R> R configure(Class<H> helper, Function<H, R> configurationOrQuery) {
        H helperInstance = (H) helperMap.computeIfAbsent(helper, (h) -> {
            try {
                return (DiagramHelper) h.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Cannot instantiate " + h, e);
            }
        });

        return configurationOrQuery.apply(helperInstance);
    }

    private void postprocess() {
        for (DiagramHelper helper : helperMap.values()) {
            metadata.putAll(helper.getMetadata());
        }
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultDiagram diagram = (DefaultDiagram) o;
        return Objects.equals(notes, diagram.notes) &&
                Objects.equals(types, diagram.types) &&
                Objects.equals(relationships, diagram.relationships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes, types, relationships);
    }

    @Override
    public String toString() {
        return "Diagram{" +
                "notes=" + notes +
                ", types=" + types +
                ", relationships=" + relationships +
                '}';
    }

}
