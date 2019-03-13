package cz.orany.yuml.model;

public interface TypeDefinition extends DiagramContentDefinition {

    String getName();

    DiagramDefinition getDiagramDefinition();

    InheritanceBuilder inherits(DiagramKeywords.From from);
}
