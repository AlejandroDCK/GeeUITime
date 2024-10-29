package com.renhejia.robot.display.parser.countdown

data class EventData (
    var event_list: List<EventItem> = emptyList(),
    var event_total: Int = 0,
    var isHas_more: Boolean = false
)

