package cz.orany.yuml.model

import cz.orany.yuml.export.DiagramPrinter
import cz.orany.yuml.export.YumlDiagramPrinter
import groovy.transform.CompileStatic
import spock.lang.Specification
import spock.lang.Unroll

class Diagram03Spec extends Specification {

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

    void 'uncomment code to proceed to step 8'() {
        expect:
            metaClass.getMetaMethod('buildOrderDiagram')
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
    }

    @CompileStatic
    private static Diagram buildOrderDiagram() {
        Diagram.build { Diagram d ->
            note('You can stick notes on diagrams too!', 'skyblue')

            aggregation('Customer', 'Order') {
                source cardinality: '1'
                destination cardinality: '0..*', title: 'orders'
            }

            buildOrderTypes(d)

            association('Category', 'Product') {
                bidirectional true
            }

            type 'National' inherits from type 'DeliveryMethod'
            type 'International' inherits from type 'DeliveryMethod'
        }
    }

    @CompileStatic
    private static Relationship buildOrderTypes(Diagram diagram) {
        diagram.with {
            composition('Order', 'LineItem') {
                source '*'
                destination '*'
            }

            association('Order', 'DeliveryMethod') {
                destination '1'
            }

            association('Order', 'Product') {
                source '*'
                destination '*'
            }
        }
    }
}
