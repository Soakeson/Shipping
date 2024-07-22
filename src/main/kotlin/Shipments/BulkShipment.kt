package Shipments

import ShipmentObserver
import ShipmentSubject
import ShippingStatus
import ShippingUpdate

class BulkShipment (
    id: String?,
    created: Long?
) : Shipment(id, created) {
    override var expected: Long = 0
        set(value) {
            if (value < created + 86400000 * 3) throw Error("Expected delivery date must be over 3 days past creation for a bulk shipment.")
            else {
                field = value
            }
        }
}
