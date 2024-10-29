package com.renhejia.robot.display

import android.graphics.Rect

class RobotSkinLabel {
    private var text: String? = null
    private var color: Int = 0
    private var align: Int = 0
    private var origSize: Int = 0
    private var dispSize: Int = 0
    private var style: Int = 0

    private var origRect: Rect? = null
    private var dispRect: Rect? = null
    private var dataFormat: String? = null
    private var languageFormat: String? = null // 显示中英文
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

    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
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

    fun getOrigSize(): Int {
        return origSize
    }

    fun setOrigSize(origSize: Int) {
        this.origSize = origSize
    }

    fun getDispSize(): Int {
        return dispSize
    }

    fun setDispSize(dispSize: Int) {
        this.dispSize = dispSize
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

    fun getDataFormat(): String? {
        return dataFormat
    }

    fun setDataFormat(dataFormat: String?) {
        this.dataFormat = dataFormat
    }

    fun getLanguageFormat(): String? {
        return languageFormat
    }

    fun setLaunguageFormat(launguageFormat: String?) {
        this.languageFormat = launguageFormat
    }

    fun getStyle(): Int {
        return style
    }

    fun setStyle(style: Int) {
        this.style = style
    }
}
