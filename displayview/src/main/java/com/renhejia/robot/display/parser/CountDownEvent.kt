package com.renhejia.robot.display.parser

data class CountDownEvent (
    private val event_time: Int = 0,
    private val event_title: String? = null,
    private val remain_days: Int = 0
)
