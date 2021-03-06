package cz.orany.yuml.model

import cz.orany.yuml.export.YumlDiagramPrinter
import spock.lang.Specification

class Diagram00Spec extends Specification {

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

    void 'create orders diagram'() {
        given:
            // tag::constructors[]
            Diagram diagram =  new Diagram()

            diagram.notes.add(new Note(
                text: 'You can stick notes on diagrams too!',
                color: 'skyblue'
            ))

            Type customer = new Type(name: 'Customer')
            Type order = new Type(name: 'Order')
            Type lineItem = new Type(name: 'LineItem')
            Type deliveryMethod = new Type(name: 'DeliveryMethod')
            Type product = new Type(name: 'Product')
            Type category = new Type(name: 'Category')
            Type nationalDeliveryMethod = new Type(name: 'National')
            Type internationalDeliveryMethod = new Type(name: 'International')

            diagram.types.add(customer)
            diagram.types.add(order)
            diagram.types.add(lineItem)
            diagram.types.add(deliveryMethod)
            diagram.types.add(product)
            diagram.types.add(category)
            diagram.types.add(nationalDeliveryMethod)
            diagram.types.add(internationalDeliveryMethod)

            diagram.relationships.add(new Relationship(
                source: customer,
                sourceCardinality: '1',
                destinationTitle: 'orders',
                destination: order,
                destinationCardinality: '0..*',
                type: RelationshipType.AGGREGATION
            ))

            diagram.relationships.add(new Relationship(
                source: order,
                sourceCardinality: '*',
                destination: lineItem,
                destinationCardinality: '*',
                type: RelationshipType.COMPOSITION
            ))

            diagram.relationships.add(new Relationship(
                source: order,
                destination: deliveryMethod,
                destinationCardinality: '1'
            ))

            diagram.relationships.add(new Relationship(
                source: order,
                sourceCardinality: '*',
                destination: product,
                destinationCardinality: '*'
            ))

            diagram.relationships.add(new Relationship(
                source: category,
                destination: product,
                bidirectional: true
            ))

            diagram.relationships.add(new Relationship(
                source: nationalDeliveryMethod,
                destination: deliveryMethod,
                type: RelationshipType.INHERITANCE
            ))

            diagram.relationships.add(new Relationship(
                source: internationalDeliveryMethod,
                destination: deliveryMethod,
                type: RelationshipType.INHERITANCE
            ))
            // end::constructors[]

        expect:
            normalize(new YumlDiagramPrinter().print(diagram)) == normalize(EXPECTED_DIAGRAM)
    }

    private static String normalize(String string) {
        return string.trim().replace('\\s+', " ")
    }

}
