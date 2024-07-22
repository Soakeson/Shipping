package Shipments

import ShipmentObserver
import ShipmentSubject
import ShippingStatus
import ShippingUpdate

class ExpressShipment (
    id: String?,
    created: Long?
) : Shipment(id, created) {
    override var expected: Long = 0
        set(value) {
            if (value > created + 86400000 * 3) throw Error("Expected delivery date must be within 3 days of creation for an express shipment.")
            else {
                field = value
            }
        }
}
