package Updates

import Shipments.Shipment
import Enums.Status

class Note(): Update {
    override var status: Status = Status.NoteAdded
    override fun update(shipment: Shipment, param: String?) {
        try {
            shipment.notes.add(param!!)
        } catch(e: Error) {
            throw Error("${e}: Invalid note.")
        }
        shipment.status = status
        shipment.notifySubscribers()
    }
}
