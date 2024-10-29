package com.renhejia.robot.display

import android.graphics.Rect

class RobotSkinArc {
    private var color: Int = 0
    private var align: Int = 0
    private var startAngle: Int = 0
    private var sweepAngle: Int = 0
    private var strokeWidth: Int = 0

    private var origRect: Rect? = null
    private var dispRect: Rect? = null
    private var origTouchRect: Rect? = null
    private var dispTouchRect: Rect? = null

    fun getOrigRect(): Rect? {
        return origRect
    }

    fun setOrigRect(origRect: Rect?) {
        this.origRect = origRect
    }

    fun getDispRect(): Rect? {
        return dispRect
    }

    fun setDispRect(dispRect: Rect?) {
        this.dispRect = dispRect
    }

    fun getColor(): Int {
        return -0x1000000 or color
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun getAlign(): Int {
        return align
    }

    fun setAlign(align: Int) {
        this.align = align
    }

    fun getOrigTouchRect(): Rect? {
        return origTouchRect
    }

    fun setOrigTouchRect(origTouchRect: Rect?) {
        this.origTouchRect = origTouchRect
    }

    fun getDispTouchRect(): Rect? {
        return dispTouchRect
    }

    fun setDispTouchRect(dispTouchRect: Rect?) {
        this.dispTouchRect = dispTouchRect
    }

    fun getStartAngle(): Int {
        return startAngle
    }

    fun setStartAngle(startAngle: Int) {
        this.startAngle = startAngle
    }

    fun getSweepAngle(): Int {
        return sweepAngle
    }

    fun setSweepAngle(sweepAngle: Int) {
        this.sweepAngle = sweepAngle
    }

    fun getStrokeWidth(): Float {
        return strokeWidth.toFloat()
    }

    fun setStrokeWidth(strokeWidth: Int) {
        this.strokeWidth = strokeWidth
    }
}
