package cz.orany.yuml.model;

import java.util.Objects;

public class DefaultType implements TypeDefinition, Type {

    private final DefaultDiagram diagram;
    private final String name;

    public DefaultType(DefaultDiagram diagram, String name) {
        this.diagram = diagram;
        this.name = name;
    }

    @Override
    public InheritanceBuilder inherits(DiagramKeywords.From from) {
        return new InheritanceBuilder(diagram, this);
    }

    @Override
    public String getName() {
        return name;
    }

    public DiagramDefinition getDiagramDefinition() {
        return diagram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultType type = (DefaultType) o;
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
