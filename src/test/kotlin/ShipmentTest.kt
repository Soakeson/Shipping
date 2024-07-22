import Shipments.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ShipmentTest {

    @Test
    fun testStandardShipmentConstruct() {
        assertNotNull(StandardShipment("1", 123))
    }

    @Test
    fun testBulkShipmentConstruct() {
        assertNotNull(BulkShipment("1", 123))
    }

    @Test
    fun testOvernightShipmentConstruct() {
        assertNotNull(OvernightShipment("1", 123))
    }

    @Test
    fun testExpressShipmentConstruct() {
        assertNotNull(ExpressShipment("1", 123))
    }

    @Test
    fun testShipmentSubscribe() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        assertEquals(1, shipment.subscribers.size)
    }

    @Test
    fun testShipmentUnsubscribe() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        shipment.unsubscribe(tracker)
        assertEquals(0, shipment.subscribers.size)
    }

    @Test
    fun testShipmentShipped() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        StatusUpdate(ShippingStatus.Shipped, "123").updateShipment(shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(ShippingStatus.Shipped, shipment.status)
        assertEquals(ShippingStatus.Shipped, tracker.status.value)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentLocation() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        LocationUpdate("123", "Salt Lake").updateShipment(shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(ShippingStatus.Location, shipment.status)
        assertEquals(ShippingStatus.Location, tracker.status.value)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentDelayed() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        DateUpdate(ShippingStatus.Delayed, "123", "1234").updateShipment(shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(ShippingStatus.Delayed, shipment.status)
        assertEquals(ShippingStatus.Delayed, tracker.status.value)
        assertEquals(1234, shipment.expected)
        assertEquals(1234, tracker.expected.value)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentAddNote() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        NoteUpdate("123", "hello").updateShipment(shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(ShippingStatus.NoteAdded, shipment.status)
        assertEquals(ShippingStatus.NoteAdded, tracker.status.value)
        assertEquals(1, shipment.notes.size)
        assertEquals(1, tracker.notes.size)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }
}