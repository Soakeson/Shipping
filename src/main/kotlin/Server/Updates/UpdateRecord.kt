package Server.Updates

import Enums.Status

data class UpdateRecord(
    var curr: Status,
    var prev: Status,
    var time: Long
)