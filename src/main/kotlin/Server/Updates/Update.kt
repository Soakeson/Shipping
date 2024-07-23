package Updates

import Enums.Status
import Shipments.Shipment

interface Update {
    var status: Status
    fun update(shipment: Shipment, param: String? = null)
}




