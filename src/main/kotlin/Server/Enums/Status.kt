package Enums

enum class Status(var status: String) {
    Created("created"),
    Shipped("shipped"),
    Lost("lost"),
    Cancelled("cancelled"),
    Delivered("delivered"),
    NoteAdded("noteadded"),
    Location("location"),
    Delayed("delayed"),
}