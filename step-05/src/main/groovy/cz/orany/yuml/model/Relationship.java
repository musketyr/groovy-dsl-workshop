package cz.orany.yuml.model;

public interface Relationship extends DiagramContentDefinition {
    RelationshipType getType();

    boolean isBidirectional();

    Type getSource();

    String getSourceCardinality();

    String getSourceTitle();

    Type getDestination();

    String getDestinationCardinality();

    String getDestinationTitle();
}
