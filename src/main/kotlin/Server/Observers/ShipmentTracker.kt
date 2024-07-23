package Server.Observers

import Enums.Status
import Server.Updates.UpdateRecord
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import Shipments.Shipment
import Updates.Update

class ShipmentTracker (
    private val shipment: Shipment
) : ShipmentObserver {
    var id = mutableStateOf(shipment.id)
    var status = mutableStateOf(shipment.status)
    var expected = mutableStateOf(shipment.expected)
    var location = mutableStateOf(shipment.location)
    var updates = shipment.updates.toMutableStateList()
    var notes = shipment.notes.toMutableStateList()

    init {
        shipment.subscribe(observer = this)
    }

    override fun update(status: Status, expected: Long, location: String, updates: List<UpdateRecord>, notes: List<String>) {
        this.status.value = status
        this.expected.value = expected
        this.location.value = location
        this.updates = updates.toMutableStateList()
        this.notes = notes.toMutableStateList()
    }
}