import Shipments.Shipment

interface ShippingUpdate {
    var curr: ShippingStatus
    var time: String?
}

class StatusUpdate(
    override var curr: ShippingStatus,
    override var time: String?,
    shipment: Shipment

): ShippingUpdate {
    init {
        shipment.status = curr
        shipment.updates.add(this)
        shipment.notifySubscribers()
    }
}

class NoteUpdate(
    override var time: String?,
    note: String?,
    shipment: Shipment
): ShippingUpdate {
    override var curr: ShippingStatus = ShippingStatus.NoteAdded
    init {
        shipment.status = curr
        note?.let { shipment.notes.add(it) }
        shipment.updates.add(this)
        shipment.notifySubscribers()
    }
}

class LocationUpdate(
    override var time: String?,
    location: String?,
    shipment: Shipment
): ShippingUpdate {
    override var curr: ShippingStatus = ShippingStatus.Location
    init {
        shipment.status = curr
        location?.let { shipment.location = it }
        shipment.updates.add(this)
        shipment.notifySubscribers()
    }
}

class DateUpdate (
    override var curr: ShippingStatus,
    override var time: String?,
    expected: String?,
    shipment: Shipment
): ShippingUpdate {
    init {
        shipment.status = curr
        expected?.let { shipment.expected = it.toLong() }
        shipment.updates.add(this)
        shipment.notifySubscribers()
    }
}