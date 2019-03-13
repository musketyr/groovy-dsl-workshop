package cz.orany.yuml.model;

import java.util.Objects;

public class Type implements DiagramContent {

    private final Diagram diagram;
    private final String name;

    public Type(Diagram diagram, String name) {
        this.diagram = diagram;
        this.name = name;
    }

    public InheritanceBuilder inherits(DiagramKeywords.From from) {
        return new InheritanceBuilder(diagram, this);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                '}';
    }

}
