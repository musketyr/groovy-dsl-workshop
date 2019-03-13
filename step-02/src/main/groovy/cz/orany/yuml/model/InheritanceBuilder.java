package cz.orany.yuml.model;

public class InheritanceBuilder {

    private final Diagram diagram;
    private final Type type;

    public InheritanceBuilder(Diagram diagram, Type type) {

        this.diagram = diagram;
        this.type = type;
    }

    public Relationship type(String parent) {
        return diagram.inheritance(type.getName(), parent, c-> {});
    }
}
