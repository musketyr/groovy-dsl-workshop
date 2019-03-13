package cz.orany.yuml.model;

import java.util.Objects;

public class Relationship {

    private RelationshipType type = RelationshipType.ASSOCIATION;
    private boolean bidirectional;
    private Type source;
    private String sourceCardinality;
    private String sourceTitle;
    private Type destination;
    private String destinationCardinality;
    private String destinationTitle;public RelationshipType getType() {
        return type;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public Type getSource() {
        return source;
    }

    public void setSource(Type source) {
        this.source = source;
    }

    public String getSourceCardinality() {
        return sourceCardinality;
    }

    public void setSourceCardinality(String sourceCardinality) {
        this.sourceCardinality = sourceCardinality;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public Type getDestination() {
        return destination;
    }

    public void setDestination(Type destination) {
        this.destination = destination;
    }

    public String getDestinationCardinality() {
        return destinationCardinality;
    }

    public void setDestinationCardinality(String destinationCardinality) {
        this.destinationCardinality = destinationCardinality;
    }

    public String getDestinationTitle() {
        return destinationTitle;
    }

    public void setDestinationTitle(String destinationTitle) {
        this.destinationTitle = destinationTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
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
