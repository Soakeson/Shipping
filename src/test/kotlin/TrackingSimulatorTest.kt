import org.testng.Assert.assertNotNull
import org.testng.annotations.Test


class TrackingSimulatorTest {
    @Test
    fun testSimulatorConstruction() {
        assertNotNull(TrackingSimulator())
    }

    @Test
    fun testSimulationUpdate() {
        val simulator = TrackingSimulator()
        simulator.update()
        assertNotNull(simulator.trackShipment("s10000"))
        simulator.update()
        assertNotNull(simulator.trackShipment("s10001"))
        simulator.update()
        assertNotNull(simulator.trackShipment("s10002"))
    }
}