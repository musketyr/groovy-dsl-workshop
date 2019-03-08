package cz.orany.yuml.model

import groovy.transform.CompileStatic

@CompileStatic
enum RelationshipType {
    ASSOCIATION,
    AGGREGATION,
    COMPOSITION,
    INHERITANCE
}
