import kotlinx.coroutines.delay
import Shipments.Shipment
import Shipments.StandardShipment

class TrackingSimulator {
    private var running = false
    var shipments: MutableMap<String, Shipment> = mutableMapOf()
    var csv = CsvMapping("status", "id", "time", "result")
    var fileMapper = FileMapper(
        FileType.CSV,
        path = "test.txt",
        "status", "id", "time", "result"
    )

    suspend fun run() {
        running = true
        while (running) {
            update()
            delay(1000)
        }
    }

    fun update() {
        val update = fileMapper.mapNextLine()
        if (update == null) {
            running = false
        }
        if (update != null) {
            updateShipment(update)
            }
        }

    private fun updateShipment(update: Map<String, String>) {
        val shipment =
            update["id"]?.let { findShipment(it) ?: StandardShipment(update["id"], update["time"]?.toLong()) }
        if (shipment != null) {
            val status = ShippingStatus.values().find { it.status == update["status"] } ?: return
            when (status.type) {
                UpdateType.Date -> {
                    DateUpdate(status, update["time"], update["result"], shipment)
                }

                UpdateType.Location -> {
                    LocationUpdate(update["time"], update["result"], shipment)
                }

                UpdateType.Note -> {
                    NoteUpdate(update["time"], update["result"], shipment)
                }

                UpdateType.Status -> {
                    StatusUpdate(status, update["time"], shipment)
                }

                UpdateType.Created -> {
                    StatusUpdate(status, update["time"], shipment)
                    shipments[shipment.id] = shipment
                }
            }
        }
    }


    fun stop() {
        running = false
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
