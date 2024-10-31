package com.renhejia.robot.display

import android.graphics.Point
import android.graphics.Rect

class RobotSkinAnalogTime {
    private var origRect: Rect? = null
    private var dispRect: Rect? = null
    private var origTouchRect: Rect? = null
    private var dispTouchRect: Rect? = null

    private var filePrefix: String? = null

    private var origHourAnchor: Point? = null
    private var origMinuteAnchor: Point? = null
    private var origSecondAnchor: Point? = null

    private var dispHourAnchor: Point? = null
    private var dispMinuteAnchor: Point? = null
    private var dispSecondAnchor: Point? = null

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

    fun getFilePrefix(): String? {
        return filePrefix
    }

    fun setFilePrefix(filePrefix: String?) {
        this.filePrefix = filePrefix
    }


    fun getHourFilename(): String {
        return filePrefix + "hour.png"
    }

    fun getMinuteFilename(): String {
        return filePrefix + "minute.png"
    }

    fun getSecondFilename(): String {
        return filePrefix + "second.png"
    }

    fun getOrigHourAnchor(): Point? {
        return origHourAnchor
    }

    fun setOrigHourAnchor(origHourAnchor: Point?) {
        this.origHourAnchor = origHourAnchor
    }

    fun getOrigMinuteAnchor(): Point? {
        return origMinuteAnchor
    }

    fun setOrigMinuteAnchor(origMinuteAnchor: Point?) {
        this.origMinuteAnchor = origMinuteAnchor
    }

    fun getOrigSecondAnchor(): Point? {
        return origSecondAnchor
    }

    fun setOrigSecondAnchor(origSecondAnchor: Point?) {
        this.origSecondAnchor = origSecondAnchor
    }

    fun getDispHourAnchor(): Point? {
        return dispHourAnchor
    }

    fun setDispHourAnchor(dispHourAnchor: Point?) {
        this.dispHourAnchor = dispHourAnchor
    }

    fun getDispMinuteAnchor(): Point? {
        return dispMinuteAnchor
    }

    fun setDispMinuteAnchor(dispMinuteAnchor: Point?) {
        this.dispMinuteAnchor = dispMinuteAnchor
    }

    fun getDispSecondAnchor(): Point? {
        return dispSecondAnchor
    }

    fun setDispSecondAnchor(dispSecondAnchor: Point?) {
        this.dispSecondAnchor = dispSecondAnchor
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
}
