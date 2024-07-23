import Server.Observers.ShipmentObserver

interface ShipmentSubject {
    fun subscribe(observer: ShipmentObserver)
    fun unsubscribe(observer: ShipmentObserver)
    fun notifySubscribers()
}
