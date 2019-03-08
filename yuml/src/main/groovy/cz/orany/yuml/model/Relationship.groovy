package cz.orany.yuml.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
class Relationship {

    RelationshipType type = RelationshipType.ASSOCIATION
    boolean bidirectional

    Type source
    String sourceCardinality
    String sourceTitle

    Type destination
    String destinationCardinality
    String destinationTitle

}
