import Enums.Status
import Server.Shipments.Shipper
import Shipments.ExpressShipment
import Updates.Delayed
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull


class ShippingControllerTest {
    @Test
    fun testControllerConstruction() {
        assertNotNull(ShippingController)
    }

    @Test
    fun testUpdate() {
    }

    @Test
    fun testTrackShipment() {
        val shipment = Shipper.new("test", "express")
        ShippingController.shipments[shipment.id] = shipment
        assertNotNull(ShippingController.trackShipment("test"))
    }

    @Test
    fun testAllUpdatesArePresent() {
        for (status in Status.values()) {
            println(status)
            assertNotNull(ShippingController.updates[status.status])
        }
    }
}