import Enums.Status
import Server.Observers.ShipmentTracker
import Server.Shipments.Shipper
import Shipments.Shipment
import Shipments.StandardShipment
import Updates.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipmentTrackerTest {
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
        val shipment = Shipper.new("1", "bulk")
        Created().update(shipment)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        Shipped().update(shipment, (System.currentTimeMillis() + (86400000 * 3) + 1).toString())
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Shipped, shipment.status)
        assertEquals(Status.Shipped, tracker.status.value)
        assertEquals(2, shipment.updates.size)
        assertEquals(2, tracker.updates.size)
    }

    @Test
    fun testShipmentLocation() {
        val shipment = Shipper.new("1", "standard")
        Created().update(shipment)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        Location().update(shipment, "Salt Lake")
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Location, shipment.status)
        assertEquals(Status.Location, tracker.status.value)
        assertEquals(2, shipment.updates.size)
        assertEquals(2, tracker.updates.size)
    }

    @Test
    fun testShipmentDelayed() {
        val shipment = Shipper.new("1", "express")
        Created().update(shipment)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        val time = System.currentTimeMillis()
        Delayed().update(shipment, time.toString())
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Delayed, shipment.status)
        assertEquals(Status.Delayed, tracker.status.value)
        assertEquals(time, shipment.expected)
        assertEquals(time, tracker.expected.value)
        assertEquals(2, shipment.updates.size)
        assertEquals(2, tracker.updates.size)
    }

    @Test
    fun testShipmentAddNote() {
        val shipment = Shipper.new("1", "overnight")
        Created().update(shipment)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        Note().update(shipment, "test")
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.NoteAdded, shipment.status)
        assertEquals(Status.NoteAdded, tracker.status.value)
        assertEquals(1, shipment.notes.size)
        assertEquals(1, tracker.notes.size)
        assertEquals(1, shipment.updates.size)
        assertEquals(1, tracker.updates.size)
    }

    @Test
    fun testShipmentCancelled() {
        val shipment = Shipper.new("1", "express")
        Cancelled().update(shipment)
        val tracker = ShipmentTracker(shipment)
        shipment.subscribe(tracker)
        Cancelled().update(shipment)
        assertEquals(tracker.expected.value, shipment.expected)
        assertEquals(Status.Cancelled, shipment.status)
        assertEquals(Status.Cancelled, tracker.status.value)
        assertEquals(2, shipment.updates.size)
        assertEquals(2, tracker.updates.size)
    }
}