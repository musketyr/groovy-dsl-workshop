package cz.orany.yuml.model;

import java.util.*;

public class Diagram {

    private Collection<Note> notes = new LinkedHashSet<>();
    private Map<String, Type> types = new LinkedHashMap<>();
    private Collection<Relationship> relationships = new LinkedHashSet<>();

    public Note note(String text) {
        return note(text, null);
    }

    public Note note(String text, String color) {
        Note note = new Note(text, color);
        notes.add(note);
        return note;
    }

    public Type type(String name) {
        return types.computeIfAbsent(name, Type::new);
    }

    public Relationship relationship(Type source, Type destination) {
        return relationship(source, RelationshipType.ASSOCIATION, destination);
    }

    public Relationship relationship(Type source, RelationshipType type, Type destination) {
        Relationship relationship = new Relationship(source, type, destination);
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
