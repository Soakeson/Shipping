package Server.Updates

import Enums.Status
import Shipments.Shipment
import Updates.Update

class Lost : Update {
    override var status: Status = Status.Lost
    override fun update(shipment: Shipment, param: String?) {
        shipment.updates.add(UpdateRecord(status, shipment.status, System.currentTimeMillis()))
        shipment.status = status
        shipment.notifySubscribers()
    }
}
