package Server.Enums

enum class ShipmentType(var type: String) {
    Bulk("bulk"),
    Express("express"),
    Overnight("overnight"),
    Standard("standard")
}