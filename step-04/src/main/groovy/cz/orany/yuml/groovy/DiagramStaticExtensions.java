package cz.orany.yuml.groovy;

import cz.orany.yuml.model.Diagram;
import cz.orany.yuml.model.DiagramContent;
import cz.orany.yuml.model.DiagramKeywords;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import space.jasan.support.groovy.closure.ConsumerWithDelegate;

public class DiagramStaticExtensions {

    public static Diagram build(
        Diagram self,
        @DelegatesTo(value = Diagram.class, strategy = Closure.DELEGATE_FIRST)
        @ClosureParams(value = SimpleType.class, options = "cz.orany.yuml.model.Diagram")
        Closure<? extends DiagramContent> definition
    ) {
        return Diagram.create(ConsumerWithDelegate.create(definition));
    }

    public static DiagramKeywords.From getFrom(Diagram self) {
        return DiagramKeywords.From.FROM;
    }
}
