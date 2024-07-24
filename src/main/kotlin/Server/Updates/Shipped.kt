package Updates

import Shipments.Shipment
import Enums.Status
import Server.Updates.UpdateRecord

class Shipped : Update {
    override var status: Status = Status.Shipped
    override fun update(shipment: Shipment, param: String?) {
        try {
            shipment.expected = param!!.toLong()
        } catch(e: Exception) {
            println(e.message)
            throw Exception("${e}: Invalid expected date.")
        }
        shipment.updates.add(UpdateRecord(status, shipment.status, System.currentTimeMillis()))
        shipment.status = status
        shipment.notifySubscribers()
    }
}