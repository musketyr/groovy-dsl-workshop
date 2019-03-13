package cz.orany.yuml.model;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Diagram {

    public static Diagram create(Consumer<Diagram> diagramConsumer) {
        Diagram diagram = new Diagram();
        diagramConsumer.accept(diagram);
        diagram.postprocess();
        return diagram;
    }

    private Collection<Note> notes = new LinkedHashSet<>();
    private Map<String, Type> types = new LinkedHashMap<>();
    private Collection<Relationship> relationships = new LinkedHashSet<>();
    private Map<Class, DiagramHelper> helperMap = new LinkedHashMap<>();
    private Map<String, Object> metadata = new LinkedHashMap<>();

    public Note note(String text) {
        return note(text, null);
    }

    public Note note(String text, String color) {
        Note note = new Note(text, color);
        notes.add(note);
        return note;
    }

    public Type type(String name) {
        return types.computeIfAbsent(name, n -> new Type(this, n));
    }

    public Type type(String name, Consumer<Type> configuration) {
        Type type = type(name);
        configuration.accept(type);
        return type;
    }

    public Relationship association(String source, String destination, Consumer<Relationship> configuration) {
        return relationship(source, RelationshipType.ASSOCIATION, destination, configuration);
    }

    public Relationship aggregation(String source, String destination, Consumer<Relationship> configuration) {
        return relationship(source, RelationshipType.AGGREGATION, destination, configuration);
    }

    public Relationship inheritance(String source, String destination, Consumer<Relationship> configuration) {
        return relationship(source, RelationshipType.INHERITANCE, destination, configuration);
    }

    public Relationship composition(String source, String destination, Consumer<Relationship> configuration) {
        return relationship(source, RelationshipType.COMPOSITION, destination, configuration);
    }

    public Relationship relationship(String source, RelationshipType type, String destination, Consumer<Relationship> configuration) {
        Relationship relationship = new Relationship(type(source), type, type(destination));
        configuration.accept(relationship);
        relationships.add(relationship);
        return relationship;
    }

    public Collection<Note> getNotes() {
        return Collections.unmodifiableCollection(notes);
    }

    public Collection<Type> getTypes() {
        return Collections.unmodifiableCollection(types.values());
    }

    public Collection<Relationship> getRelationships() {
        return Collections.unmodifiableCollection(relationships);
    }

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

    void postprocess() {
        for (DiagramHelper helper : helperMap.values()) {
            metadata.putAll(helper.getMetadata());
        }
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagram diagram = (Diagram) o;
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
