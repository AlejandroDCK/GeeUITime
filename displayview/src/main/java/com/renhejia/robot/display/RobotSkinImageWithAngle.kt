package com.renhejia.robot.display

import android.graphics.Point

class RobotSkinImageWithAngle : RobotSkinImage() {
    private var intervalAngle: Int = 0
    private var imageAngle: Int = 0
    private var startAngle: Int = 0
    private var total: Int = 0

    private var origAnchor: Point? = null
    private var displayAnchor: Point? = null

    fun getTotal(): Int {
        return total
    }

    fun setTotal(totalBatteryCount: Int) {
        this.total = totalBatteryCount
    }

    fun getStartAngle(): Int {
        return startAngle
    }

    fun setStartAngle(startAngle: Int) {
        this.startAngle = startAngle
    }

    fun getIntervalAngle(): Int {
        return intervalAngle
    }

    fun setIntervalAngle(intervalAngle: Int) {
        this.intervalAngle = intervalAngle
    }

    fun getOrigAnchor(): Point? {
        return origAnchor
    }

    fun setOrigAnchor(origAnchor: Point?) {
        this.origAnchor = origAnchor
    }

    fun getDisplayAnchor(): Point? {
        return displayAnchor
    }

    fun setDisplayAnchor(displayAnchor: Point?) {
        this.displayAnchor = displayAnchor
    }

    fun getImageAngle(): Int {
        return imageAngle
    }

    fun setImageAngle(imageAngle: Int) {
        this.imageAngle = imageAngle
    }
}
