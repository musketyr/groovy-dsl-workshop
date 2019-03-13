package cz.orany.yuml.model;

import java.util.Objects;

public class DefaultRelationship implements DiagramContentDefinition, Relationship, RelationshipDefinition {

    private final RelationshipType type;
    private boolean bidirectional;
    private final Type source;
    private String sourceCardinality;
    private String sourceTitle;
    private final Type destination;
    private String destinationCardinality;
    private String destinationTitle;

    @Override
    public RelationshipType getType() {
        return type;
    }

    public DefaultRelationship(Type source, RelationshipType type, Type destination) {
        this.type = type;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public Relationship bidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
        return this;
    }

    @Override
    public Relationship source(String cardinality, String title) {
        this.sourceCardinality = cardinality;
        this.sourceTitle = title;
        return this;
    }

    @Override
    public Relationship destination(String cardinality, String title) {
        this.destinationCardinality = cardinality;
        this.destinationTitle = title;
        return this;
    }

    @Override
    public boolean isBidirectional() {
        return bidirectional;
    }

    @Override
    public Type getSource() {
        return source;
    }

    @Override
    public String getSourceCardinality() {
        return sourceCardinality;
    }

    @Override
    public String getSourceTitle() {
        return sourceTitle;
    }

    @Override
    public Type getDestination() {
        return destination;
    }

    @Override
    public String getDestinationCardinality() {
        return destinationCardinality;
    }


    @Override
    public String getDestinationTitle() {
        return destinationTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultRelationship that = (DefaultRelationship) o;
        return bidirectional == that.bidirectional &&
                type == that.type &&
                Objects.equals(source, that.source) &&
                Objects.equals(sourceCardinality, that.sourceCardinality) &&
                Objects.equals(sourceTitle, that.sourceTitle) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(destinationCardinality, that.destinationCardinality) &&
                Objects.equals(destinationTitle, that.destinationTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, bidirectional, source, sourceCardinality, sourceTitle, destination, destinationCardinality, destinationTitle);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "type=" + type +
                ", bidirectional=" + bidirectional +
                ", source=" + source +
                ", sourceCardinality='" + sourceCardinality + '\'' +
                ", sourceTitle='" + sourceTitle + '\'' +
                ", destination=" + destination +
                ", destinationCardinality='" + destinationCardinality + '\'' +
                ", destinationTitle='" + destinationTitle + '\'' +
                '}';
    }
}
