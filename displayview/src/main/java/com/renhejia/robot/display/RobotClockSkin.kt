package com.renhejia.robot.display

import android.graphics.Point
import android.graphics.Rect
import kotlin.math.min

class RobotClockSkin {
    var analogTimes: List<RobotSkinAnalogTime> = ArrayList()
    var digitTimes: List<RobotSkinNumber> = ArrayList()
    var labelTimes: List<RobotSkinLabel> = ArrayList()

    var step: RobotSkinNumber? = null
    var airTemp: RobotSkinNumber? = null

    var battery: RobotSkinImage? = null
    var wifi: RobotSkinImage? = null
    var bluetooth: RobotSkinImage? = null
    var weather: RobotSkinImage? = null
    var notices: RobotSkinImage? = null // todo new
    var weatherFeatureTime: List<RobotSkinLabel>? = ArrayList()
    var weatherFeatureTemp: List<RobotSkinLabel>? = ArrayList()
    var weatherFeatureIcon: List<RobotSkinImage>? = ArrayList()
    var backgrounds: List<RobotSkinAnchor>? = ArrayList() // todo new
    var volume: RobotSkinImage? = null
    var charge: RobotSkinImage? = null
    var background: RobotSkinImage? = null
    var foreground: RobotSkinImage? = null
    var middle: RobotSkinImage? = null
    var batterySquares: RobotSkinImageWithSpace? = null
    var stepSquares: RobotSkinImageWithSpace? = null

    var batteryAnchor: RobotSkinAnchor? = null
    var weekAnchor: RobotSkinAnchor? = null

    var stepNumber: RobotSkinLabel? = null
    var aqiNumber: RobotSkinLabel? = null
    var aqiText: RobotSkinLabel? = null
    var noticeText: RobotSkinLabel? = null // todo new
    var batteryNumber: RobotSkinLabel? = null
    var temperature: RobotSkinLabel? = null
    var temperatureInfo: RobotSkinLabel? = null
    var temperatureRange: RobotSkinLabel? = null
    var temperatureDes: RobotSkinLabel? = null
    private val windPower: RobotSkinLabel? = null

    private val weatherInfo: List<RobotSkinLabel> = ArrayList()
    var countdownEvent: List<RobotSkinLabel>? = ArrayList() // todo new
    var notice: List<RobotSkinLabel>? = ArrayList() // todo new
    var fansInfo: List<RobotSkinLabel>? = ArrayList() // todo new
    var fansIcon: RobotSkinImage? = null // todo new
    var fansHead: RobotSkinImage? = null // todo new

    var batteryProgress: RobotSkinShape? = null

    var stepSkinArc: RobotSkinArc? = null
    var batteryAngle: RobotSkinImageWithAngle? = null

    var origRect: Rect? = null
    private var dispRect: Rect? = null

    private var xRadio: Float = 1.0f
    private var yRadio: Float = 1.0f

    var videoTotal: Int = 0

    var resPool: SpineSkinResPool? = null

    fun getxRadio(): Float {
        return xRadio
    }

    fun getyRadio(): Float {
        return yRadio
    }

    fun calcDispRect(origRect: Rect, xRadio: Float, yRadio: Float): Rect {
        val left: Float = origRect.left * xRadio
        val top: Float = origRect.top * yRadio
        val right: Float = origRect.right * xRadio
        val bottom: Float = origRect.bottom * yRadio
        return Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
    }

    fun calcDispPoint(origPoint: Point, xRadio: Float, yRadio: Float): Point {
        val x: Float = origPoint.x * xRadio
        val y: Float = origPoint.y * yRadio
        return Point(x.toInt(), y.toInt())
    }

    fun resize(rect: Rect) {
        if (dispRect !== rect && origRect != null) {
            dispRect = rect

            xRadio = rect.width().toFloat() / origRect!!.width()
            yRadio = rect.height().toFloat() / origRect!!.height()

            if (background != null) {
                background!!.setDispRect(calcDispRect(background!!.getOrigRect()!!, xRadio, yRadio))
            }

            if (backgrounds != null) {
                val bgs: List<RobotSkinAnchor>? = backgrounds

                for (bg: RobotSkinAnchor in bgs!!) {
                    bg.setDispRect(calcDispRect(bg.getOrigRect()!!, xRadio, yRadio))
                    bg.setDispAnchor(calcDispPoint(bg.getOrigAnchor()!!, xRadio, yRadio))
                }
            }


            if (foreground != null) {
                foreground!!.setDispRect(calcDispRect(foreground!!.getOrigRect()!!, xRadio, yRadio))
            }

            if (middle != null) {
                middle!!.setDispRect(calcDispRect(middle!!.getOrigRect()!!, xRadio, yRadio))
            }

            if (batteryProgress != null) {
                batteryProgress!!.setDispRect(
                    calcDispRect(
                        batteryProgress!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )
            }

            if (stepSkinArc != null) {
                stepSkinArc!!.setDispRect(stepSkinArc!!.getOrigRect())
            }

            if (aqiNumber != null) {
                aqiNumber!!.setDispRect(calcDispRect(aqiNumber!!.getOrigRect()!!, xRadio, yRadio))

                if (aqiNumber!!.getOrigTouchRect() != null) {
                    aqiNumber!!.setDispTouchRect(
                        calcDispRect(
                            aqiNumber!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    aqiNumber!!.setDispTouchRect(
                        calcDispRect(
                            aqiNumber!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (aqiNumber!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                aqiNumber!!.setDispSize(dispSize.toInt())
            }

            if (stepNumber != null) {
                stepNumber!!.setDispRect(calcDispRect(stepNumber!!.getOrigRect()!!, xRadio, yRadio))

                if (stepNumber!!.getOrigTouchRect() != null) {
                    stepNumber!!.setDispTouchRect(
                        calcDispRect(
                            stepNumber!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    stepNumber!!.setDispTouchRect(
                        calcDispRect(
                            stepNumber!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (stepNumber!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                stepNumber!!.setDispSize(dispSize.toInt())
            }
            if (batteryNumber != null) {
                batteryNumber!!.setDispRect(
                    calcDispRect(
                        batteryNumber!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )

                if (batteryNumber!!.getOrigTouchRect() != null) {
                    batteryNumber!!.setDispTouchRect(
                        calcDispRect(
                            batteryNumber!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    batteryNumber!!.setDispTouchRect(
                        calcDispRect(
                            batteryNumber!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (batteryNumber!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                batteryNumber!!.setDispSize(dispSize.toInt())
            }
            if (temperature != null) {
                temperature!!.setDispRect(
                    calcDispRect(
                        temperature!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )

                if (temperature!!.getOrigTouchRect() != null) {
                    temperature!!.setDispTouchRect(
                        calcDispRect(
                            temperature!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    temperature!!.setDispTouchRect(
                        calcDispRect(
                            temperature!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (temperature!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                temperature!!.setDispSize(dispSize.toInt())
            }
            if (temperatureDes != null) {
                temperatureDes!!.setDispRect(
                    calcDispRect(
                        temperatureDes!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )

                if (temperatureDes!!.getOrigTouchRect() != null) {
                    temperatureDes!!.setDispTouchRect(
                        calcDispRect(
                            temperatureDes!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    temperatureDes!!.setDispTouchRect(
                        calcDispRect(
                            temperatureDes!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (temperatureDes!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                temperatureDes!!.setDispSize(dispSize.toInt())
            }
            if (temperatureInfo != null) {
                temperatureInfo!!.setDispRect(
                    calcDispRect(
                        temperatureInfo!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )

                if (temperatureInfo!!.getOrigTouchRect() != null) {
                    temperatureInfo!!.setDispTouchRect(
                        calcDispRect(
                            temperatureInfo!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    temperatureInfo!!.setDispTouchRect(
                        calcDispRect(
                            temperatureInfo!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (temperatureInfo!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                temperatureInfo!!.setDispSize(dispSize.toInt())
            }
            if (temperatureRange != null) {
                temperatureRange!!.setDispRect(
                    calcDispRect(
                        temperatureRange!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )

                if (temperatureRange!!.getOrigTouchRect() != null) {
                    temperatureRange!!.setDispTouchRect(
                        calcDispRect(
                            temperatureRange!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    temperatureRange!!.setDispTouchRect(
                        calcDispRect(
                            temperatureRange!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (temperatureRange!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                temperatureRange!!.setDispSize(dispSize.toInt())
            }

            if (aqiText != null) {
                aqiText!!.setDispRect(calcDispRect(aqiText!!.getOrigRect()!!, xRadio, yRadio))
                if (aqiText!!.getOrigTouchRect() != null) {
                    aqiText!!.setDispTouchRect(
                        calcDispRect(
                            aqiText!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    aqiText!!.setDispTouchRect(
                        calcDispRect(
                            aqiText!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (aqiText!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                aqiText!!.setDispSize(dispSize.toInt())
            }

            if (noticeText != null) {
                noticeText!!.setDispRect(calcDispRect(noticeText!!.getOrigRect()!!, xRadio, yRadio))
                if (noticeText!!.getOrigTouchRect() != null) {
                    noticeText!!.setDispTouchRect(
                        calcDispRect(
                            noticeText!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    noticeText!!.setDispTouchRect(
                        calcDispRect(
                            noticeText!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }

                val dispSize: Float =
                    (noticeText!!.getOrigSize() * (min(
                        xRadio.toDouble(),
                        yRadio.toDouble()
                    ))).toFloat()
                noticeText!!.setDispSize(dispSize.toInt())
            }

            if (charge != null) {
                charge!!.setDispRect(calcDispRect(charge!!.getOrigRect()!!, xRadio, yRadio))
            }
            if (step != null) {
                step!!.setDispRect(calcDispRect(step!!.getOrigRect()!!, xRadio, yRadio))
                if (step!!.getOrigTouchRect() != null) {
                    step!!.setDispTouchRect(
                        calcDispRect(
                            step!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    step!!.setDispTouchRect(calcDispRect(step!!.getOrigRect()!!, xRadio, yRadio))
                }
            }

            if (weather != null) {
                weather!!.setDispRect(calcDispRect(weather!!.getOrigRect()!!, xRadio, yRadio))
                if (weather!!.getOrigTouchRect() != null) {
                    weather!!.setDispTouchRect(
                        calcDispRect(
                            weather!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    weather!!.setDispTouchRect(
                        calcDispRect(
                            weather!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }
            if (notices != null) {
                notices!!.setDispRect(calcDispRect(notices!!.getOrigRect()!!, xRadio, yRadio))
                if (notices!!.getOrigTouchRect() != null) {
                    notices!!.setDispTouchRect(
                        calcDispRect(
                            notices!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    notices!!.setDispTouchRect(
                        calcDispRect(
                            notices!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }
            if (fansIcon != null) {
                fansIcon!!.setDispRect(calcDispRect(fansIcon!!.getOrigRect()!!, xRadio, yRadio))
                if (fansIcon!!.getOrigTouchRect() != null) {
                    fansIcon!!.setDispTouchRect(
                        calcDispRect(
                            fansIcon!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    fansIcon!!.setDispTouchRect(
                        calcDispRect(
                            fansIcon!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }
            if (fansHead != null) {
                fansHead!!.setDispRect(calcDispRect(fansHead!!.getOrigRect()!!, xRadio, yRadio))
                if (fansHead!!.getOrigTouchRect() != null) {
                    fansHead!!.setDispTouchRect(
                        calcDispRect(
                            fansHead!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    fansHead!!.setDispTouchRect(
                        calcDispRect(
                            fansHead!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }

            if (airTemp != null) {
                airTemp!!.setDispRect(calcDispRect(airTemp!!.getOrigRect()!!, xRadio, yRadio))
                if (airTemp!!.getOrigTouchRect() != null) {
                    airTemp!!.setDispTouchRect(
                        calcDispRect(
                            airTemp!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    airTemp!!.setDispTouchRect(
                        calcDispRect(
                            airTemp!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }

            if (wifi != null) {
                wifi!!.setDispRect(calcDispRect(wifi!!.getOrigRect()!!, xRadio, yRadio))
                if (wifi!!.getOrigTouchRect() != null) {
                    wifi!!.setDispTouchRect(
                        calcDispRect(
                            wifi!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    wifi!!.setDispTouchRect(calcDispRect(wifi!!.getOrigRect()!!, xRadio, yRadio))
                }
            }

            if (bluetooth != null) {
                bluetooth!!.setDispRect(calcDispRect(bluetooth!!.getOrigRect()!!, xRadio, yRadio))
                if (bluetooth!!.getOrigTouchRect() != null) {
                    bluetooth!!.setDispTouchRect(
                        calcDispRect(
                            bluetooth!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    bluetooth!!.setDispTouchRect(
                        calcDispRect(
                            bluetooth!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }

            if (volume != null) {
                volume!!.setDispRect(calcDispRect(volume!!.getOrigRect()!!, xRadio, yRadio))
                if (volume!!.getOrigTouchRect() != null) {
                    volume!!.setDispTouchRect(
                        calcDispRect(
                            volume!!.getOrigTouchRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                } else {
                    volume!!.setDispTouchRect(
                        calcDispRect(
                            volume!!.getOrigRect()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }

            if (battery != null) {
                battery!!.setDispRect(calcDispRect(battery!!.getOrigRect()!!, xRadio, yRadio))
            }


            if (batteryAnchor != null) {
                batteryAnchor!!.setDispRect(
                    calcDispRect(
                        batteryAnchor!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )
                batteryAnchor!!.setDispAnchor(
                    calcDispPoint(
                        batteryAnchor!!.getOrigAnchor()!!,
                        xRadio,
                        yRadio
                    )
                )
            }

            if (weekAnchor != null) {
                weekAnchor!!.setDispRect(calcDispRect(weekAnchor!!.getOrigRect()!!, xRadio, yRadio))
                weekAnchor!!.setDispAnchor(
                    calcDispPoint(
                        weekAnchor!!.getOrigAnchor()!!,
                        xRadio,
                        yRadio
                    )
                )
            }

            if (batterySquares != null) {
                batterySquares!!.setDispRect(
                    calcDispRect(
                        batterySquares!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )
            }

            if (stepSquares != null) {
                stepSquares!!.setDispRect(
                    calcDispRect(
                        stepSquares!!.getOrigRect()!!,
                        xRadio,
                        yRadio
                    )
                )
            }

            if (weatherFeatureTemp != null) {
                val wts: List<RobotSkinLabel>? = weatherFeatureTemp

                for (wt: RobotSkinLabel in wts!!) {
                    wt.setDispRect(calcDispRect(wt.getOrigRect()!!, xRadio, yRadio))
                }
            }

            if (weatherFeatureTime != null) {
                val wts: List<RobotSkinLabel>? = weatherFeatureTime

                for (wt: RobotSkinLabel in wts!!) {
                    wt.setDispRect(calcDispRect(wt.getOrigRect()!!, xRadio, yRadio))
                }
            }

            if (weatherFeatureIcon != null) {
                val wis: List<RobotSkinImage>? = weatherFeatureIcon

                for (wi: RobotSkinImage in wis!!) {
                    wi.setDispRect(calcDispRect(wi.getOrigRect()!!, xRadio, yRadio))
                }
            }

            if (countdownEvent != null) {
                val ces: List<RobotSkinLabel>? = countdownEvent
                for (ce: RobotSkinLabel in ces!!) {
                    ce.setDispRect(calcDispRect(ce.getOrigRect()!!, xRadio, yRadio))
                    val dispSize: Float =
                        (ce.getOrigSize() * (min(
                            xRadio.toDouble(),
                            yRadio.toDouble()
                        ))).toFloat()
                    ce.setDispSize(dispSize.toInt())
                }
            }
            if (fansInfo != null) {
                val infos: List<RobotSkinLabel>? = fansInfo
                for (info: RobotSkinLabel in infos!!) {
                    info.setDispRect(calcDispRect(info.getOrigRect()!!, xRadio, yRadio))
                    val dispSize: Float =
                        (info.getOrigSize() * (min(
                            xRadio.toDouble(),
                            yRadio.toDouble()
                        ))).toFloat()
                    info.setDispSize(dispSize.toInt())
                }
            }
            //notice 和 notice名字需要调整
            if (notice != null) {
                val notices: List<RobotSkinLabel>? = notice
                for (notice: RobotSkinLabel in notices!!) {
                    notice.setDispRect(calcDispRect(notice.getOrigRect()!!, xRadio, yRadio))
                    val dispSize: Float =
                        (notice.getOrigSize() * (min(
                            xRadio.toDouble(),
                            yRadio.toDouble()
                        ))).toFloat()
                    notice.setDispSize(dispSize.toInt())
                }
            }

            val lts: List<RobotSkinLabel> = labelTimes
            for (lt: RobotSkinLabel in lts) {
                lt.setDispRect(calcDispRect(lt.getOrigRect()!!, xRadio, yRadio))
                val dispSize: Float =
                    (lt.getOrigSize() * (min(xRadio.toDouble(), yRadio.toDouble()))).toFloat()
                lt.setDispSize(dispSize.toInt())
            }

            val dts: List<RobotSkinNumber> = digitTimes

            for (dt: RobotSkinNumber in dts) {
                dt.setDispRect(calcDispRect(dt.getOrigRect()!!, xRadio, yRadio))
            }

            val ats: List<RobotSkinAnalogTime> = analogTimes

            for (at: RobotSkinAnalogTime in ats) {
                at.setDispRect(calcDispRect(at.getOrigRect()!!, xRadio, yRadio))
                at.setDispHourAnchor(calcDispPoint(at.getOrigHourAnchor()!!, xRadio, yRadio))
                at.setDispMinuteAnchor(calcDispPoint(at.getOrigMinuteAnchor()!!, xRadio, yRadio))
                if (at.getOrigSecondAnchor() != null) {
                    at.setDispSecondAnchor(
                        calcDispPoint(
                            at.getOrigSecondAnchor()!!,
                            xRadio,
                            yRadio
                        )
                    )
                }
            }
        }
    }


    val isRefreshPerSecond: Boolean
        get() {
            val analogTimes: List<RobotSkinAnalogTime>? = analogTimes
            if (analogTimes != null && analogTimes.size > 0) {
                for (i in analogTimes.indices) {
                    if ((analogTimes.get(i) != null) && (analogTimes.get(i)
                            .getOrigSecondAnchor() != null)
                    ) {
                        return true
                    }
                }
            }
            return false
        }

    companion object {
        const val ALIGN_LEFT: Int = 0x0
        const val ALIGN_CENTER: Int = 0x1
        const val ALIGN_RIGHT: Int = 0x2

        const val ALIGN_TOP: Int = 0x0
        const val ALIGN_MIDDLE: Int = 0x4
        const val ALIGN_BOTTOM: Int = 0x8

        const val ALIGN_LEFT_TO_RIGHT: Int = 0x0
        const val ALIGN_RIGHT_TO_LEFT: Int = 0x1
        const val ALIGN_BOTTOM_TO_TOP: Int = 0x2
        const val ALIGN_TOP_TO_BOTTOM: Int = 0x3

        val WEATHER_TYPE_NO_INFO: Int = -1

        const val WEATHER_TYPE_SUNNY: Int = 0
        const val WEATHER_TYPE_CLOUDY: Int = 1
        const val WEATHER_TYPE_RAIN: Int = 2
        const val WEATHER_TYPE_HAIL: Int = 3
        const val WEATHER_TYPE_SNOW: Int = 4
        const val WEATHER_TYPE_SAND_DUST: Int = 5
        const val WEATHER_TYPE_HAZE: Int = 6
        const val WEATHER_TYPE_THUNDER: Int = 7
        const val WEATHER_TYPE_WIND: Int = 8

        const val WEATHER_TYPE_FOG: Int = 9
        const val WEATHER_TYPE_RAIN_HAIL: Int = 10
        const val WEATHER_TYPE_RAIN_SNOW: Int = 11
        const val WEATHER_TYPE_RAIN_THUNDER: Int = 12

        const val CTRL_NONE_ID: Int = 0
        const val CTRL_BLUETOOTH_ID: Int = 1
        const val CTRL_MEDIA_VOLUME_ID: Int = 2
        const val CTRL_WIFI_ID: Int = 3
        const val CTRL_STEP_ID: Int = 4
        const val CTRL_WEATHER_ID: Int = 5

        const val STYLE_DEFAULT: Int = 0
        const val STYLE_ITALIC: Int = 1
    }
}
