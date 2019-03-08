package cz.orany.yuml.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
class Type {

    String name

    @Override
    String toString() {
        return "[$name]"
    }
}
