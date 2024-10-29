package com.renhejia.robot.display

class RobotSkinImageWithSpace : RobotSkinImage() {
    private var fileSpace: Int = 0
    private var aligns: Int = 0
    private var total: Int = 0

    fun getFileSpace(): Int {
        return fileSpace
    }

    fun setFileSpace(fileSpace: Int) {
        this.fileSpace = fileSpace
    }

    fun getAligns(): Int {
        return aligns
    }

    fun setAligns(aligns: Int) {
        this.aligns = aligns
    }

    fun getTotal(): Int {
        return total
    }

    fun setTotal(totalBatteryCount: Int) {
        this.total = totalBatteryCount
    }
}
