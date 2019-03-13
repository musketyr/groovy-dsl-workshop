package cz.orany.yuml.groovy;

import cz.orany.yuml.model.Diagram;
import cz.orany.yuml.model.Relationship;
import cz.orany.yuml.model.RelationshipType;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.NamedParam;
import groovy.transform.NamedParams;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import space.jasan.support.groovy.closure.ConsumerWithDelegate;

import java.util.Map;

// tag::header[]
public class DiagramExtensions {

    private static final String CARDINALITY = "cardinality";
    private static final String TITLE = "title";
    // end::header[]

    public static Relationship association(
            Diagram diagram,
            String source,
            String destination,
            @DelegatesTo(value = Relationship.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Relationship")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.ASSOCIATION, destination, configuration);
    }

    public static Relationship aggregation(
            Diagram diagram,
            String source,
            String destination,
            @DelegatesTo(value = Relationship.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Relationship")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.AGGREGATION, destination, configuration);
    }

    public static Relationship inheritance(
            Diagram diagram,
            String source,
            String destination,
            @DelegatesTo(value = Relationship.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Relationship")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.INHERITANCE, destination, configuration);
    }

    public static Relationship composition(
            Diagram diagram,
            String source,
            String destination,
            @DelegatesTo(value = Relationship.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Relationship")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.COMPOSITION, destination, configuration);
    }

    // tag::methods[]
    public static Relationship relationship(
            Diagram diagram,
            String source,
            RelationshipType type,
            String destination,
            @DelegatesTo(value = Relationship.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Relationship")
            Closure<Relationship> configuration
    ) {
        return diagram.relationship(source, type, destination, ConsumerWithDelegate.create(configuration));
    }


    public static Relationship source(                                                  // <1>
            Relationship self,                                                          // <2>
            @NamedParams({                                                              // <3>
                    @NamedParam(value = CARDINALITY, type = String.class),              // <4>
                    @NamedParam(value = TITLE, type = String.class)
            })
                    Map<String, String> cardinalityAndTitle                             // <5>
    ) {
        return self.source(
                cardinalityAndTitle.get(CARDINALITY),
                cardinalityAndTitle.get(TITLE)
        );
    }
    // end::methods[]

    public static Relationship destination(
            Relationship self,
            @NamedParams({
                    @NamedParam(value = CARDINALITY, type = String.class),
                    @NamedParam(value = TITLE, type = String.class)
            })
                    Map<String, String> cardinalityAndTitle
    ) {
        return self.destination(
                cardinalityAndTitle.get(CARDINALITY),
                cardinalityAndTitle.get(TITLE)
        );
    }

}
