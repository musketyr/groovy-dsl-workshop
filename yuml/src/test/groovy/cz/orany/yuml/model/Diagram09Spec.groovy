package cz.orany.yuml.model

import cz.orany.yuml.export.DiagramPrinter
import cz.orany.yuml.export.YumlDiagramPrinter
import cz.orany.yuml.model.Diagram
import cz.orany.yuml.model.dsl.DiagramContentDefinition
import cz.orany.yuml.model.dsl.DiagramDefinition
import groovy.transform.CompileStatic
import spock.lang.Specification
import spock.lang.Unroll

import static cz.orany.yuml.model.dsl.DiagramKeywords.*

class Diagram09Spec extends Specification {

    private static final String EXPECTED_DIAGRAM = '''
        [note: You can stick notes on diagrams too!{bg:skyblue}]
        [Customer]<>1-orders 0..*>[Order]
        [Order]++*-*>[LineItem]
        [Order]-1>[DeliveryMethod]
        [Order]*-*>[Product]
        [Category]<->[Product]
        [DeliveryMethod]^[National]
        [DeliveryMethod]^[International]
    '''.stripIndent().trim()

    private static final String EXPECTED_DIAGRAM_DIAGRAM = '''
        [note: YUML Diagram Components]
        [Type]<>1..*->[Diagram]
        [Note]<>0..*->[Diagram]
        [Relationship]<>0..*->[Diagram]
        [Type]<>source 1->[Relationship]
        [Type]<>destination 1->[Relationship]
        [RelationshipType]++1->[Relationship]
    '''.stripIndent().trim()

    private static final String EXPECTED_DIAGRAM_STEROTYPES = '''
        [note: YUML Diagram Components]
        [<<Type>>]<>1..*->[<<Diagram>>]
        [<<Note>>]<>0..*->[<<Diagram>>]
        [<<Relationship>>]<>0..*->[<<Diagram>>]
        [<<Type>>]<>source 1->[<<Relationship>>]
        [<<Type>>]<>destination 1->[<<Relationship>>]
        [<<RelationshipType>>]++1->[<<Relationship>>]
    '''.stripIndent().trim()

    private static final String EXPECTED_DIAGRAM_PROPERTIES = '''
        [note: YUML Diagram Components]
        [Type|name:string]<>1..*->[Diagram]
        [Note]<>0..*->[Diagram]
        [Relationship]<>0..*->[Diagram]
        [Type|name:string]<>source 1->[Relationship]
        [Type|name:string]<>destination 1->[Relationship]
        [RelationshipType]++1->[Relationship]
    '''.stripIndent().trim()

    void 'uncomment code to proceed to step 8'() {
        expect:
            metaClass.getMetaMethod('buildOrderDiagram')
            metaClass.getMetaMethod('buildDiagramDiagramLiteral')
            metaClass.getMetaMethod('buildDiagramDiagramGrouped')
            metaClass.getMetaMethod('buildDiagramDiagramUsingHelperMethods')
            metaClass.getMetaMethod('buildDiagramWithInternalMethodCalls')
            metaClass.getMetaMethod('buildDiagramStereotypes')
            metaClass.getMetaMethod('buildDiagramProperties')
    }

    @Unroll
    void 'create #title diagram'() {
        given:
            DiagramPrinter printer = new YumlDiagramPrinter()
        expect:
            printer.print(diagram).trim() == expected

            diagram.relationships*.source.every { it in diagram.types }
            diagram.relationships*.destination.every { it in diagram.types }
        where:
            title                           | diagram                                 | expected
            'orders'                        | buildOrderDiagram()                     | EXPECTED_DIAGRAM
            'literal diagram'               | buildDiagramDiagramLiteral()            | EXPECTED_DIAGRAM_DIAGRAM
            'literal diagram'               | buildDiagramDiagramGrouped()            | EXPECTED_DIAGRAM_DIAGRAM
            'diagram with methods'          | buildDiagramDiagramUsingHelperMethods() | EXPECTED_DIAGRAM_DIAGRAM
            'diagram with internal methods' | buildDiagramWithInternalMethodCalls()   | EXPECTED_DIAGRAM_DIAGRAM
            'diagram with stereotypes'      | buildDiagramStereotypes()               | EXPECTED_DIAGRAM_STEROTYPES
            'diagram with properties'       | buildDiagramProperties()                | EXPECTED_DIAGRAM_PROPERTIES
    }

//    @CompileStatic
//    private static Diagram buildOrderDiagram() {
//        Diagram.build {
//            note('You can stick notes on diagrams too!', 'skyblue')
//
//            aggregation('Customer', 'Order') {
//                source cardinality: '1'
//                destination cardinality: '0..*', title: 'orders'
//            }
//
//            composition('Order', 'LineItem') {
//                source '*'
//                destination '*'
//            }
//
//            association('Order', 'DeliveryMethod') {
//                destination '1'
//            }
//
//            association('Order', 'Product') {
//                source '*'
//                destination '*'
//            }
//
//            association('Category', 'Product') {
//                bidirectional true
//            }
//
//            type 'National' inherits cz.orany.yuml.model.dsl.DiagramKeywords.from type 'DeliveryMethod'
//            type'International' inherits cz.orany.yuml.model.dsl.DiagramKeywords.from type 'DeliveryMethod'
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramDiagramLiteral() {
//        Diagram.build {
//            note 'YUML Diagram Components'
//
//            // diagram should have at least one type to be meaningful, rest is optional
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Type'
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Note'
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Relationship'
//
//            type 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'source'
//            type 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'destination'
//            type 'Relationship' owns cz.orany.yuml.model.dsl.DiagramKeywords.one type 'RelationshipType'
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramDiagramGrouped() {
//        Diagram.build {
//            note 'YUML Diagram Components'
//
//            // diagram should have at least one type to be meaningful, rest is optional
//            type 'Diagram', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Type'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Note'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Relationship'
//            }
//
//            type 'Relationship', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'source'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'destination'
//                owns cz.orany.yuml.model.dsl.DiagramKeywords.one type 'RelationshipType'
//            }
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramDiagramUsingHelperMethods() {
//        Diagram.build { DiagramDefinition diagram ->
//            note 'YUML Diagram Components'
//
//            buildDiagramRelationships(diagram)
//            buildRelationshipRelationship(diagram)
//        }
//    }
//
//    @CompileStatic
//    private static DiagramContentDefinition buildDiagramRelationships(DiagramDefinition diagram) {
//        diagram.with {
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Type'
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Note'
//            type 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Relationship'
//        }
//    }
//
//    @CompileStatic
//    private static DiagramContentDefinition buildRelationshipRelationship(DiagramDefinition diagram) {
//        diagram.with {
//            type 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'source'
//            type 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'destination'
//            type 'Relationship' owns cz.orany.yuml.model.dsl.DiagramKeywords.one type 'RelationshipType'
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramWithInternalMethodCalls() {
//        Diagram.build {
//            note 'YUML Diagram Components'
//
//            // diagram should have at least one type to be meaningful, rest is optional
//            type 'Diagram', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Type'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type notes
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Relationship'
//            }
//
//            type 'Relationship', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'source'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'destination'
//                owns cz.orany.yuml.model.dsl.DiagramKeywords.one type 'RelationshipType'
//            }
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramStereotypes() {
//        Diagram.build {
//            note 'YUML Diagram Components'
//
//            // diagram should have at least one stereotype to be meaningful, rest is optional
//            stereotype 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many stereotype 'Type'
//            stereotype 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many stereotype 'Note'
//            stereotype 'Diagram' has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many stereotype 'Relationship'
//
//            stereotype 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one stereotype 'Type' called 'source'
//            stereotype 'Relationship' has cz.orany.yuml.model.dsl.DiagramKeywords.one stereotype 'Type' called 'destination'
//            stereotype 'Relationship' owns cz.orany.yuml.model.dsl.DiagramKeywords.one stereotype 'RelationshipType'
//        }
//    }
//
//    @CompileStatic
//    private static Diagram buildDiagramProperties() {
//        Diagram.build {
//            note 'YUML Diagram Components'
//
//            // diagram should have at least one type to be meaningful, rest is optional
//            type 'Diagram', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Type'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type notes
//                has cz.orany.yuml.model.dsl.DiagramKeywords.zero to cz.orany.yuml.model.dsl.DiagramKeywords.many type 'Relationship'
//            }
//
//            type 'Relationship', {
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'source'
//                has cz.orany.yuml.model.dsl.DiagramKeywords.one type 'Type' called 'destination'
//                owns cz.orany.yuml.model.dsl.DiagramKeywords.one type 'RelationshipType'
//            }
//
//            type 'Type', {
//                property name: 'string'
//            }
//        }
//    }

    private static String getNotes() {
        return 'Note'
    }
}
