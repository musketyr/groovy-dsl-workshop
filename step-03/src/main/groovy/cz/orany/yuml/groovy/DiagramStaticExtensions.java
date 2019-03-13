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

    public static Diagram build(                                                        // <1>
        Diagram self,                                                                   // <2>
        @DelegatesTo(value = Diagram.class, strategy = Closure.DELEGATE_FIRST)          // <3>
        @ClosureParams(value=SimpleType.class, options="cz.orany.yuml.model.Diagram")   // <4>
        Closure<? extends DiagramContent> definition                                    // <5>
    ) {
        return Diagram.create(ConsumerWithDelegate.create(definition));                 // <6>
    }

    public static DiagramKeywords.From getFrom(Diagram self) {                          // <7>
        return DiagramKeywords.From.FROM;
    }
}
