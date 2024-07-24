import Shipments.*
import Updates.Delayed
import kotlin.test.Test
import kotlin.test.assertFails
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
    fun testInvalidBulkShipmentDate() {
        val shipment = BulkShipment("1", 123)
        assertNotNull(shipment)
        assertFails { Delayed().update(shipment, "125") }
    }

    @Test
    fun testOvernightShipmentConstruct() {
        assertNotNull(OvernightShipment("1", 123))
    }

    @Test
    fun testInvalidOvernightShipmentDate() {
        val shipment = OvernightShipment("1", 123)
        assertNotNull(shipment)
        assertFails { Delayed().update(shipment, "1721793294246") }
    }

    @Test
    fun testExpressShipmentConstruct() {
        assertNotNull(ExpressShipment("1", 123))
    }

    @Test
    fun testInvalidExpressShipmentConstruct() {
        val shipment = ExpressShipment("1", 123)
        assertNotNull(shipment)
        assertFails { Delayed().update(shipment, "1721793294246") }
    }

}