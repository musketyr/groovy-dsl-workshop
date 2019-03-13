package cz.orany.yuml.groovy;

import cz.orany.yuml.model.*;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.NamedParam;
import groovy.transform.NamedParams;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import space.jasan.support.groovy.closure.ConsumerWithDelegate;

import java.util.Map;

public class DiagramExtensions {

    private static final String CARDINALITY = "cardinality";
    private static final String TITLE = "title";

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

    public static Relationship source(
            Relationship self,
            @NamedParams({
                    @NamedParam(value = CARDINALITY, type = String.class),
                    @NamedParam(value = TITLE, type = String.class)
            })
                    Map<String, String> cardinalityAndTitle
    ) {
        return self.source(
                cardinalityAndTitle.get(CARDINALITY),
                cardinalityAndTitle.get(TITLE)
        );
    }

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

    public static Type type(
            Diagram diagram,
            String name,
            @DelegatesTo(value = Type.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Type")
            Closure configuration
    ) {
        return diagram.type(name, ConsumerWithDelegate.create(configuration));
    }

    // tag::stereotype[]
    public static Type stereotype(Diagram self, String name) {
        return self.type("<<" + name + ">>");
    }

    public static Relationship stereotype(InheritanceBuilder self, String name) {
        return self.type("<<" + name + ">>");
    }
    // end::stereotype[]

    // tag::property[]
    public static Type property(Type typeDefinition, String type, String name) {
        typeDefinition.getDiagram().configure(PropertiesDiagramHelper.class, (h) ->
            h.addProperty(typeDefinition.getName(), type, name)
        );
        return typeDefinition;
    }
    // end::property[]

}
