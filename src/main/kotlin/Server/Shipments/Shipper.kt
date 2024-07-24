package Server.Shipments

import Server.Enums.ShipmentType
import Shipments.*

object Shipper {
    fun new(id: String?, type: String?) : Shipment {
        if (id.isNullOrBlank()) {
            throw Error("Invalid shipment ID")
        }

        val shipmentType = ShipmentType.entries.firstOrNull { it.type == type } ?: throw Error("Invalid shipment type")
        try {
            return when (shipmentType) {
                ShipmentType.Bulk -> {
                    BulkShipment(id, System.currentTimeMillis())
                }

                ShipmentType.Express -> {
                    ExpressShipment(id, System.currentTimeMillis())
                }

                ShipmentType.Standard -> {
                    StandardShipment(id, System.currentTimeMillis())
                }

                ShipmentType.Overnight -> {
                    OvernightShipment(id, System.currentTimeMillis())
                }
            }
        } catch(e: Exception) {
            throw Error(e.message ?: "Unknown error")
        }
    }
}