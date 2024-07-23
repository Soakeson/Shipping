package Updates

import Shipments.Shipment
import Enums.Status
import Server.Updates.UpdateRecord

class Cancelled : Update {
    override var status: Status = Status.Cancelled
    override fun update(shipment: Shipment, param: String?) {
        shipment.updates.add(UpdateRecord(status, shipment.status, System.currentTimeMillis()))
        shipment.status = status
        shipment.notifySubscribers()
    }
}
