package cz.orany.yuml.model;

public interface RelationshipDefinition extends DiagramContentDefinition {
    Relationship bidirectional(boolean bidirectional);

    default Relationship source(String cardinality) {
        return source(cardinality, null);
    }

    Relationship source(String cardinality, String title);

    default Relationship destination(String cardinality) {
        return destination(cardinality, null);
    }

    Relationship destination(String cardinality, String title);
}
