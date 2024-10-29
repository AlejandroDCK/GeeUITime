package com.renhejia.robot.display.parser.countdown

data class EventItem (
    var event_time: Long = 0,
    var event_title: String? = null,
    var remain_days: Int = 0
)
