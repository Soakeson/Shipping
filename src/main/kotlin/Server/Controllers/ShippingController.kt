import Mappers.CsvMapper
import Server.Observers.ShipmentTracker
import Server.Updates.Lost
import Shipments.Shipment
import Shipments.StandardShipment
import Updates.*

object ShipmentController {
    var csv = CsvMapper("status", "id", "time", "result")
    var shipments: MutableMap<String, Shipment> = mutableMapOf()
    var updates: Map<String, Update> = mapOf(
        Pair<String, Update>("created", Created()),
        Pair<String, Update>("noteadded", Note()),
        Pair<String, Update>("shipped", Shipped()),
        Pair<String, Update>("lost", Lost()),
        Pair<String, Update>("delivered", Delivered()),
        Pair<String, Update>("canceled", Cancelled()),
        Pair<String, Update>("location", Location()),
        Pair<String, Update>("delayed", Delayed())
    )


    fun update(request: String) {
        val update = csv.mapString(request)
        val shipment = update["id"]?.let { findShipment(it) ?: StandardShipment(update["id"], update["time"]?.toLong()) }
        if (shipment != null) {
        }
    }

    private fun findShipment(id: String): Shipment? {
        return shipments[id]
    }

    fun trackShipment(id: String): ShipmentTracker? {
        val shipment = this.findShipment(id)
        if (shipment != null) {
            val tracker = ShipmentTracker(shipment)
            shipment.subscribe(tracker)
            return tracker
        }
        return null
    }
}
