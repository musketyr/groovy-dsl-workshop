package cz.orany.yuml.groovy;

import cz.orany.yuml.model.*;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import space.jasan.support.groovy.closure.ConsumerWithDelegate;

public class DiagramStaticExtensions {

    public static Diagram build(
        Diagram self,
        @DelegatesTo(value = DiagramDefinition.class, strategy = Closure.DELEGATE_FIRST)
        @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.DiagramDefinition")
        Closure<? extends DiagramContentDefinition> definition
    ) {
        return DefaultDiagram.create(ConsumerWithDelegate.create(definition));
    }

    public static DiagramKeywords.From getFrom(DiagramDefinition self) {
        return DiagramKeywords.From.FROM;
    }
}
