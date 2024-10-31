package com.renhejia.robot.display

import android.graphics.Point
import android.graphics.Rect

class RobotSkinAnchor {
    private var origRect: Rect? = null
    private var dispRect: Rect? = null
    private var origTouchRect: Rect? = null
    private var dispTouchRect: Rect? = null

    private var imgFile: String? = null

    private var origAnchor: Point? = null
    private var dispAnchor: Point? = null

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

    fun getOrigAnchor(): Point? {
        return origAnchor
    }

    fun setOrigAnchor(origAnchor: Point?) {
        this.origAnchor = origAnchor
    }

    fun getDispAnchor(): Point? {
        return dispAnchor
    }

    fun setDispAnchor(dispAnchor: Point?) {
        this.dispAnchor = dispAnchor
    }

    fun getImgFile(): String? {
        return imgFile
    }

    fun setImgFile(imgFile: String?) {
        this.imgFile = imgFile
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
