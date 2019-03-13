package cz.orany.yuml.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

// tag::important[]
public class Diagram {

    private Collection<Note> notes = new LinkedHashSet<>();
    private Collection<Type> types = new LinkedHashSet<>();
    private Collection<Relationship> relationships = new LinkedHashSet<>();

    // end::important[]

    public Collection<Note> getNotes() {
        return notes;
    }

    public void setNotes(Collection<Note> notes) {
        this.notes = notes;
    }

    public Collection<Type> getTypes() {
        return types;
    }

    public void setTypes(Collection<Type> types) {
        this.types = types;
    }

    public Collection<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(Collection<Relationship> relationships) {
        this.relationships = relationships;
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
