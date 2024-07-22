package Shipments

import ShipmentObserver
import ShipmentSubject
import ShippingStatus
import ShippingUpdate

class OvernightShipment (
    id: String?,
    created: Long?
) : Shipment(id, created) {
    override var expected: Long = 0
        set(value) {
            if (value > created + 86400000) throw Error("Expected delivery date must be within 1 day of creation for an overnight shipment.")
            else {
                field = value
            }
        }
}
