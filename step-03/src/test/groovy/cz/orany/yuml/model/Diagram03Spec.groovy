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

    @Unroll
    void 'create #title diagram'() {
        given:
            DiagramPrinter printer = new YumlDiagramPrinter()
        expect:
            normalize(printer.print(diagram)) == normalize(expected)

            diagram.relationships*.source.every { it in diagram.types }
            diagram.relationships*.destination.every { it in diagram.types }
        where:
            title                           | diagram                                 | expected
            'orders'                        | buildOrderDiagram()                     | EXPECTED_DIAGRAM
    }

    // tag::dsl[]
    @CompileStatic                                                                      // <1>
    private static Diagram buildOrderDiagram() {
        Diagram.build { Diagram d ->                                                    // <2>
            note('You can stick notes on diagrams too!', 'skyblue')

            aggregation('Customer', 'Order') {                                          // <3>
                source cardinality: '1'                                                 // <4>
                destination cardinality: '0..*', title: 'orders'
            }

            buildOrderTypes(d)                                                          // <5>

            association('Category', 'Product') {
                bidirectional true
            }

            type 'National' inherits from type 'DeliveryMethod'
            type 'International' inherits from type 'DeliveryMethod'
        }
    }
    // end::dsl[]

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

    private static String normalize(String string) {
        return string.trim().replace('\\s+', " ")
    }
}
