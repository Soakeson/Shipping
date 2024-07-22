enum class ShippingStatus(var status: String, var type: UpdateType) {
    Created("created", UpdateType.Created),
    Shipped("shipped", UpdateType.Date),
    Lost("lost", UpdateType.Status),
    Canceled("canceled", UpdateType.Status),
    Delivered("delivered", UpdateType.Status),
    NoteAdded("noteadded", UpdateType.Note),
    Location("location", UpdateType.Location),
    Delayed("delayed", UpdateType.Date),
}

enum class UpdateType() {
    Note,
    Date,
    Location,
    Status,
    Created
}