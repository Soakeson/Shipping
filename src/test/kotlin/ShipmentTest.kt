import Enums.Status
import Shipments.*
import Updates.DateUpdate
import Updates.LocationUpdate
import Updates.NoteUpdate
import Updates.StatusUpdate
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotNull
import org.testng.annotations.Test

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
        StatusUpdate(Status.Shipped, "123", shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Shipped, shipment.status)
        assertEquals(Status.Shipped, tracker.status.value)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentLocation() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        LocationUpdate("123", "Salt Lake", shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Location, shipment.status)
        assertEquals(Status.Location, tracker.status.value)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentDelayed() {
        val shipment = StandardShipment("1", 123)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        DateUpdate(Status.Delayed, "123", "1234", shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Delayed, shipment.status)
        assertEquals(Status.Delayed, tracker.status.value)
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
        NoteUpdate("123", "hello", shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.NoteAdded, shipment.status)
        assertEquals(Status.NoteAdded, tracker.status.value)
        assertEquals(1, shipment.notes.size)
        assertEquals(1, tracker.notes.size)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }
}