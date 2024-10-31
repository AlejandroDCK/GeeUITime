package com.renhejia.robot.display

import android.graphics.Rect

class RobotSkinShape {
    private var bgColor: Int = 0
    private var fgColor: Int = 0
    private var align: Int = 0

    //    private int origSize;
    //    private int dispSize;
    private var origRect: Rect? = null
    private var dispRect: Rect? = null

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


    fun getBgColor(): Int {
        return -0x1000000 or bgColor
    }

    fun setBgColor(bgColor: Int) {
        this.bgColor = bgColor
    }

    fun getFgColor(): Int {
        return -0x1000000 or fgColor
    }

    fun setFgColor(fgColor: Int) {
        this.fgColor = fgColor
    }

    fun getAlign(): Int {
        return align
    }

    fun setAlign(align: Int) {
        this.align = align
    } //    public int getOrigSize() {
    //        return origSize;
    //    }
    //
    //    public void setOrigSize(int origSize) {
    //        this.origSize = origSize;
    //    }
    //
    //    public int getDispSize() {
    //        return dispSize;
    //    }
    //
    //    public void setDispSize(int dispSize) {
    //        this.dispSize = dispSize;
    //    }
}
