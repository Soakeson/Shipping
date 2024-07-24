import Mappers.CsvMapper
import Server.Observers.ShipmentTracker
import Server.Shipments.Shipper
import Server.Updates.Lost
import Shipments.Shipment
import Shipments.StandardShipment
import Updates.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


object ShippingController {
    var csv = CsvMapper("status", "id", "result", "type")
    var shipments: MutableMap<String, Shipment> = mutableMapOf()
    var updates: Map<String, Update> = mapOf(
        Pair<String, Update>("created", Created()),
        Pair<String, Update>("noteadded", Note()),
        Pair<String, Update>("shipped", Shipped()),
        Pair<String, Update>("lost", Lost()),
        Pair<String, Update>("delivered", Delivered()),
        Pair<String, Update>("cancelled", Cancelled()),
        Pair<String, Update>("location", Location()),
        Pair<String, Update>("delayed", Delayed())
    )

    private fun findShipment(id: String): Shipment? {
        return shipments[id]
    }

    fun trackShipment(id: String): ShipmentTracker? {
        val shipment = this.findShipment(id)
        if (shipment != null) {
            val tracker = ShipmentTracker(shipment)
            return tracker
        }
        return null
    }

    fun Route.update() {
        post("/update") {
            try {
                val update = csv.mapString(call.receiveText())
                val shipment = update["id"]?.let { it1 -> findShipment(it1) } ?: Shipper.new(update["id"], update["type"])
                if (update["status"] in updates) {
                    println(update)
                    updates[update["status"]]!!.update(shipment, update["result"])
                }
                shipments[shipment.id] = shipment
                call.respond(HttpStatusCode.OK, "Shipment Updated")
            } catch (e: Exception) {
                println(e.message)
                call.respond(HttpStatusCode.BadRequest, "${e.message}: Bad Request")
            }
        }
    }
}
