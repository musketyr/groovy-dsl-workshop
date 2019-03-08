package cz.orany.yuml.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
class Diagram {

    Collection<Note> notes = new LinkedHashSet<>()
    Collection<Type> types = new LinkedHashSet<>()
    Collection<Relationship> relationships = new LinkedHashSet<>()

}
