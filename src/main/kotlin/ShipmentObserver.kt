interface ShipmentObserver {
    fun update(shippingStatus: ShippingStatus, expected: Long, location: String, updates: List<ShippingUpdate>, notes: List<String>) {}
}
