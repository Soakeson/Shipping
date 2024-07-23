package Updates

import Shipments.Shipment
import Enums.Status
import Server.Updates.UpdateRecord

class Delivered : Update {
    override var status: Status = Status.Delivered
    override fun update(shipment: Shipment, param: String?) {
        shipment.updates.add(UpdateRecord(status, shipment.status, System.currentTimeMillis()))
        shipment.status = status
        shipment.notifySubscribers()
    }
}
