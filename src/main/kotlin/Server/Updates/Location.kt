package Updates

import Shipments.Shipment
import Enums.Status
import Server.Updates.UpdateRecord

class Location(): Update {
    override var status: Status = Status.Location
    override fun update(shipment: Shipment, param: String?) {
        try {
            shipment.location = param!!
        } catch(e: Error) {
            throw Error("${e}: Invalid location.")
        }
        shipment.updates.add(UpdateRecord(status, shipment.status, System.currentTimeMillis()))
        shipment.status = status
        shipment.notifySubscribers()
    }
}