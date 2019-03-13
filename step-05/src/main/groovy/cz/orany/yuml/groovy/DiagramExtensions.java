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
            DiagramDefinition diagram,
            String source,
            String destination,
            @DelegatesTo(value = RelationshipDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.RelationshipDefinition")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.ASSOCIATION, destination, configuration);
    }

    public static Relationship aggregation(
            DiagramDefinition diagram,
            String source,
            String destination,
            @DelegatesTo(value = RelationshipDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.RelationshipDefinition")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.AGGREGATION, destination, configuration);
    }

    public static Relationship inheritance(
            DiagramDefinition diagram,
            String source,
            String destination,
            @DelegatesTo(value = RelationshipDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.RelationshipDefinition")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.INHERITANCE, destination, configuration);
    }

    public static Relationship composition(
            DiagramDefinition diagram,
            String source,
            String destination,
            @DelegatesTo(value = RelationshipDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.RelationshipDefinition")
            Closure<Relationship> configuration
    ) {
        return relationship(diagram, source, RelationshipType.COMPOSITION, destination, configuration);
    }

    public static Relationship relationship(
            DiagramDefinition diagram,
            String source,
            RelationshipType type,
            String destination,
            @DelegatesTo(value = RelationshipDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.RelationshipDefinition")
            Closure<Relationship> configuration
    ) {
        return diagram.relationship(source, type, destination, ConsumerWithDelegate.create(configuration));
    }

    public static Relationship source(
            RelationshipDefinition self,
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
            RelationshipDefinition self,
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

    public static TypeDefinition type(
            DiagramDefinition diagram,
            String name,
            @DelegatesTo(value = TypeDefinition.class, strategy = Closure.DELEGATE_FIRST)
            @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.TypeDefinition")
            Closure configuration
    ) {
        return diagram.type(name, ConsumerWithDelegate.create(configuration));
    }

    public static TypeDefinition stereotype(DiagramDefinition self, String name) {
        return self.type("<<" + name + ">>");
    }

    public static Relationship stereotype(InheritanceBuilder self, String name) {
        return self.type("<<" + name + ">>");
    }

    public static TypeDefinition property(TypeDefinition typeDefinition, String type, String name) {
        typeDefinition.getDiagramDefinition().configure(PropertiesDiagramHelper.class, (PropertiesDiagramHelper helper) -> {
            helper.addProperty(typeDefinition.getName(), type, name);
            return null;
        });
        return typeDefinition;
    }

}
