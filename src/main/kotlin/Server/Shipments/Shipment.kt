package Shipments

import Server.Observers.ShipmentObserver
import Server.Subjects.ShipmentSubject
import Enums.Status
import Server.Updates.UpdateRecord

abstract class Shipment (
    id: String?,
    created: Long?
    ) : ShipmentSubject {

    var id: String = id.let { it ?: throw Error("Id can't be null") }
        set(value) {
            field = value
            notifySubscribers()
        }

    var status: Status = Status.Created
        set(value) {
            field = value
            notifySubscribers()
        }

    open var expected: Long = 0
        set(value) {
            field = value
            notifySubscribers()
        }

    var location: String = ""
        set(value) {
            field = value
            notifySubscribers()
        }

    var notes: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            notifySubscribers()
        }

    var updates: MutableList<UpdateRecord> = mutableListOf()
        set(value) {
            field = value
            notifySubscribers()
        }

    var subscribers:MutableList<ShipmentObserver> = mutableListOf()
        set(value) {
            field = value
            notifySubscribers()
        }

    var created: Long = created.let { created ?: throw Error("Created can't be null.") }
        private set

    override fun notifySubscribers() {
        for (obs in subscribers) {
            obs.update(status, expected, location, updates, notes)
        }
    }

    override fun subscribe(observer: ShipmentObserver) {
        if (observer in subscribers) {
            return
        }
        subscribers.add(element = observer)
    }

    override fun unsubscribe(observer: ShipmentObserver) {
        if (observer !in subscribers) {
            return
        }
        subscribers.remove(element = observer)
    }
}
