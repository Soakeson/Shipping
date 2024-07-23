package Server.Observers

import Enums.Status
import Server.Updates.UpdateRecord

interface ShipmentObserver {
    fun update(status: Status, expected: Long, location: String, updates: List<UpdateRecord>, notes: List<String>) {}
}
