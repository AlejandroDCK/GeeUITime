package com.renhejia.robot.display.manager

import android.graphics.Point
import android.graphics.Rect
import com.renhejia.robot.display.RobotClockSkin
import com.renhejia.robot.display.RobotSkinAnalogTime
import com.renhejia.robot.display.RobotSkinAnchor
import com.renhejia.robot.display.RobotSkinArc
import com.renhejia.robot.display.RobotSkinImage
import com.renhejia.robot.display.RobotSkinImageWithAngle
import com.renhejia.robot.display.RobotSkinImageWithSpace
import com.renhejia.robot.display.RobotSkinLabel
import com.renhejia.robot.display.RobotSkinNumber
import com.renhejia.robot.display.RobotSkinShape
import com.renhejia.robot.display.utils.ClockViewConsts
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


object RobotSkinJsonConversionTools : ClockViewConsts {
    fun getSpineClockSkin(json: String): RobotClockSkin {
        val spineClockSkin: RobotClockSkin = RobotClockSkin()
        try {
            val `object`: JSONObject = JSONObject(json)

            val origRect: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.ORIG_RECT)
            val background: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.BACKGROUND)
            val foreground: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.FOREGROUND)
            val middle: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.MIDDLE)
            val battery: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.BATTERY)
            val wifi: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.WIFI)
            val bluetooth: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.BLUETOOTH)
            val weather: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.WEATHER)
            val notice: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.NOTICE)
            val volume: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.VOLUME)
            val charge: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.CHARGE)

            val step: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.STEP)
            val airTemp: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.AIR_TEMP)

            val batterySquares: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.BATTERY_SQUARES)
            val stepSquares: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.STEP_SQUARES)

            val batteryAnchor: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.BATTERY_ANCHOR)
            val weekAnchor: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.WEEK_ANCHOR)

            val stepNumber: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.STEP_NUMBER)
            val aqiNumber: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.AQI_NUMBER)
            val aqiText: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.AQI_TEXT)
            val noticeText: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.NOTICE_TEXT)
            val batteryNumber: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.BATTERY_NUMBER)
            val temperature: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.TEMPERATURE)

            val batteryProgress: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.BATTERY_PROGRESS)

            val stepSkinArc: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.STEP_SKIN_ARC)
            val batteryAngle: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.BATTERY_ANGLE)

            val digitTimes: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.DIGIT_TIMES)
            val labelTimes: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.LABEL_TIMES)
            val analogTimes: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.ANALOG_TIMES)
            val backgrounds: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.BACKGROUNDS)
            val countdownEvent: JSONArray? =
                getTargetJSONArray(`object`, ClockViewConsts.COUNTDOWN_EVENT)
            val notices: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.NOTICES)
            val fansInfo: JSONArray? = getTargetJSONArray(`object`, ClockViewConsts.FANS_INFO)
            val fansIcon: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.FANS_ICON)
            val fansHead: JSONObject? = getTargetJsonObject(`object`, ClockViewConsts.FANS_HEAD)
            val temperatureRange: JSONObject? =
                getTargetJsonObject(`object`, ClockViewConsts.TEMPERATURE_RANGE)

            //设置 origRect
            if (origRect != null) {
                val rect: Rect? = getRect(origRect)
                if (rect != null) {
                    spineClockSkin.origRect = rect
                }
            }

            //设置 background
            if (background != null) {
                val spineSkinImageBg: RobotSkinImage = getSpineSkinImage(background)
                spineClockSkin.background = spineSkinImageBg
            }
            //
            //设置 foreground
            if (foreground != null) {
                val spineSkinImageFg: RobotSkinImage = getSpineSkinImage(foreground)
                spineClockSkin.foreground = spineSkinImageFg
            }

            //设置 middle
            if (middle != null) {
                val spineSkinImageMd: RobotSkinImage = getSpineSkinImage(middle)
                spineClockSkin.middle = spineSkinImageMd
            }

            //设置 battery
            if (battery != null) {
                val spineSkinImageBattery: RobotSkinImage = getSpineSkinImage(battery)
                spineClockSkin.battery = spineSkinImageBattery
            }

            //设置 wifi
            if (wifi != null) {
                val spineSkinImageWifi: RobotSkinImage = getSpineSkinImage(wifi)
                spineClockSkin.wifi = spineSkinImageWifi
            }

            //设置 bluetooth
            if (bluetooth != null) {
                val spineSkinImageBT: RobotSkinImage = getSpineSkinImage(bluetooth)
                spineClockSkin.bluetooth = spineSkinImageBT
            }

            //设置 weather
            if (weather != null) {
                val spineSkinImageWeather: RobotSkinImage = getSpineSkinImage(weather)
                spineClockSkin.weather = spineSkinImageWeather
            }

            //设置 notice
            if (notice != null) {
                val spineSkinImageNotice: RobotSkinImage = getSpineSkinImage(notice)
                spineClockSkin.notices = spineSkinImageNotice
            }

            //设置 头像
            if (fansIcon != null) {
                val spineSkinImageFansIcon: RobotSkinImage = getSpineSkinImage(fansIcon)
                spineClockSkin.fansIcon = spineSkinImageFansIcon
            }

            //设置 头像
            if (fansHead != null) {
                val spineSkinImageFansHead: RobotSkinImage = getSpineSkinImage(fansHead)
                spineClockSkin.fansHead = spineSkinImageFansHead
            }

            //设置 volume
            if (volume != null) {
                val spineSkinImageVolume: RobotSkinImage = getSpineSkinImage(volume)
                spineClockSkin.volume = spineSkinImageVolume
            }

            //设置 charge
            if (charge != null) {
                val spineSkinImageCharge: RobotSkinImage = getSpineSkinImage(charge)
                spineClockSkin.charge = spineSkinImageCharge
            }

            //设置 step
            if (step != null) {
                val spineSkinNumberStep: RobotSkinNumber = getSpineSkinNumber(step)
                spineClockSkin.step = spineSkinNumberStep
            }

            //设置 airTemp
            if (airTemp != null) {
//                LogUtils.logi("testFileName0","drawAirTemp:======= 000");
                val spineSkinNumberAirTemp: RobotSkinNumber = getSpineSkinNumber(airTemp)
                spineClockSkin.airTemp = spineSkinNumberAirTemp
            } else {
//                LogUtils.logi("testFileName0","drawAirTemp:======= 111");
            }

            //设置 batterySquares
            if (batterySquares != null) {
                val spineSkinImageWithSpaceBatterySquares: RobotSkinImageWithSpace =
                    getSpineSkinImageWithSpace(batterySquares)
                spineClockSkin.batterySquares = spineSkinImageWithSpaceBatterySquares
            }

            //设置 stepSquares
            if (stepSquares != null) {
                val spineSkinImageWithSpaceStepSquares: RobotSkinImageWithSpace =
                    getSpineSkinImageWithSpace(stepSquares)
                spineClockSkin.stepSquares = spineSkinImageWithSpaceStepSquares
            }

            //设置 batteryAnchor
            if (batteryAnchor != null) {
                val spineSkinAnchorBatteryAnchor: RobotSkinAnchor =
                    getSpineSkinAnchor(batteryAnchor)
                spineClockSkin.batteryAnchor = spineSkinAnchorBatteryAnchor
            }

            //设置 weekAnchor
            if (weekAnchor != null) {
                val spineSkinAnchorWeekAnchor: RobotSkinAnchor = getSpineSkinAnchor(weekAnchor)
                spineClockSkin.weekAnchor = spineSkinAnchorWeekAnchor
            }

            //设置 stepNumber
            if (stepNumber != null) {
                val spineSkinLabelStepNumber: RobotSkinLabel = getSpineSkinLabel(stepNumber)
                spineClockSkin.stepNumber = spineSkinLabelStepNumber
            }

            //设置 aqiNumber
            if (aqiNumber != null) {
                val spineSkinLabelAqiNumber: RobotSkinLabel = getSpineSkinLabel(aqiNumber)
                spineClockSkin.aqiNumber = spineSkinLabelAqiNumber
            }

            //设置 aqiText
            if (aqiText != null) {
                val spineSkinLabelAqiText: RobotSkinLabel = getSpineSkinLabel(aqiText)
                spineClockSkin.aqiText = spineSkinLabelAqiText
            }

            //设置 noticeText
            if (noticeText != null) {
                val spineSkinLabelNoticeText: RobotSkinLabel = getSpineSkinLabel(noticeText)
                spineClockSkin.noticeText = spineSkinLabelNoticeText
            }

            //设置 batteryNumber
            if (batteryNumber != null) {
                val spineSkinLabelBatteryNumber: RobotSkinLabel = getSpineSkinLabel(batteryNumber)
                spineClockSkin.batteryNumber = spineSkinLabelBatteryNumber
            }

            //设置 temperature
            if (temperature != null) {
                val spineSkinLabelTemperature: RobotSkinLabel = getSpineSkinLabel(temperature)
                spineClockSkin.temperature = spineSkinLabelTemperature
            }

            //设置 temperatureRange
            if (temperatureRange != null) {
                val spineSkinLabelTemperature: RobotSkinLabel = getSpineSkinLabel(temperatureRange)
                spineClockSkin.temperatureRange = spineSkinLabelTemperature
            }

            //设置 batteryProgress
            if (batteryProgress != null) {
                val spineSkinShapeBatteryProgress: RobotSkinShape =
                    getSpineSkinShape(batteryProgress)
                spineClockSkin.batteryProgress = spineSkinShapeBatteryProgress
            }

            //设置 batteryProgress
            if (stepSkinArc != null) {
                val spineSkinArcStep: RobotSkinArc = getSpineSkinArc(stepSkinArc)
                spineClockSkin.stepSkinArc = spineSkinArcStep
            }

            //设置 batteryAngle
            if (batteryAngle != null) {
                val SpineSkinImageWithAngleBattery: RobotSkinImageWithAngle =
                    getSpineSkinImageWithAngle(batteryAngle)
                spineClockSkin.batteryAngle = SpineSkinImageWithAngleBattery
            }

            if (digitTimes != null) {
                val digitList: List<RobotSkinNumber> = getDigitTime(digitTimes)
                spineClockSkin.digitTimes = (digitList)
            }

            if (backgrounds != null) {
                val backgroundList: List<RobotSkinAnchor> = getBackgrounds(backgrounds)
                spineClockSkin.backgrounds = (backgroundList)
            }

            if (countdownEvent != null) {
                val labelList: List<RobotSkinLabel> = getLabelTimes(countdownEvent)
                spineClockSkin.countdownEvent = (labelList)
            }

            if (fansInfo != null) {
                val labelList: List<RobotSkinLabel> = getLabels(fansInfo)
                spineClockSkin.fansInfo=(labelList)
            }

            if (notices != null) {
                val labelList: List<RobotSkinLabel> = getLabelTimes(notices)
                spineClockSkin.notice =(labelList)
            }

            if (labelTimes != null) {
                val labelList: List<RobotSkinLabel> = getLabelTimes(labelTimes)
                spineClockSkin.labelTimes = (labelList)
            }

            if (analogTimes != null) {
                val analoglList: List<RobotSkinAnalogTime> = getAnalogTimes(analogTimes)
                spineClockSkin.analogTimes = analoglList
            }
        } catch (e: Exception) {
        }

        return spineClockSkin
    }

    private fun getTargetJsonObject(`object`: JSONObject, origRect: String): JSONObject? {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = `object`.getJSONObject(origRect)
        } catch (e: JSONException) {
            return null
        }
        return jsonObject
    }

    private fun getTargetJSONArray(`object`: JSONObject, origRect: String): JSONArray? {
        var jsonArray: JSONArray? = null
        try {
            jsonArray = `object`.getJSONArray(origRect)
        } catch (e: JSONException) {
            return null
        }
        return jsonArray
    }

    private fun getTargetIntValue(`object`: JSONObject, origRect: String): Int {
        var value: Int = 0
        try {
            value = `object`.getInt(origRect)
        } catch (e: JSONException) {
            return value
        }
        return value
    }

    private fun getTargetStringValue(`object`: JSONObject, origRect: String): String? {
        var value: String? = null
        try {
            value = `object`.getString(origRect)
        } catch (e: JSONException) {
            return null
        }
        return value
    }

    /**
     *
     *
     * @param digitTimes
     * @return
     */
    private fun getDigitTime(digitTimes: JSONArray): List<RobotSkinNumber> {
        val digitList: MutableList<RobotSkinNumber> = ArrayList()

        for (i in 0 until digitTimes.length()) {
            var spineSkinNumber: RobotSkinNumber = RobotSkinNumber()

            try {
                val jsonObject: JSONObject? = digitTimes.getJSONObject(i)
                if (jsonObject != null) {
                    spineSkinNumber = getSpineSkinNumber(jsonObject)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            digitList.add(spineSkinNumber)
        }

        return digitList
    }

    /**
     * List<RobotSkinAnchor> backgrounds
     *
     * @param backgrounds
     * @return
    </RobotSkinAnchor> */
    private fun getBackgrounds(backgrounds: JSONArray): List<RobotSkinAnchor> {
        val backgroundList: MutableList<RobotSkinAnchor> = ArrayList()

        for (i in 0 until backgrounds.length()) {
            var robotSkinAnchor: RobotSkinAnchor = RobotSkinAnchor()

            try {
                val jsonObject: JSONObject? = backgrounds.getJSONObject(i)
                if (jsonObject != null) {
                    robotSkinAnchor = getSpineSkinAnchor(jsonObject)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            backgroundList.add(robotSkinAnchor)
        }

        return backgroundList
    }

    //
    //    /**
    //     * @param labelTimes
    //     * @return
    //     */
    //    private static List<RobotSkinLabel> getLabelTimes(JSONArray labelTimes) {
    //        List<RobotSkinLabel> labelList = new ArrayList<>();
    //
    //        for (int i = 0; i < labelTimes.length(); i++) {
    //            try {
    //                RobotSkinLabel spineSkinLabel = getSpineSkinLabel(labelTimes.getJSONObject(i));
    //                if (spineSkinLabel != null) {
    //                    labelList.add(spineSkinLabel);
    //                }
    //            } catch (JSONException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //        return labelList;
    //    }
    /**
     * @param labelTimes
     * @return
     */
    private fun getLabelTimes(labelTimes: JSONArray): List<RobotSkinLabel> {
        return getLabels(labelTimes)
    }

    /**
     * @param labelTimes
     * @return
     */
    private fun getLabels(labelTimes: JSONArray): List<RobotSkinLabel> {
        val labelList: MutableList<RobotSkinLabel> = ArrayList()

        for (i in 0 until labelTimes.length()) {
            try {
                val spineSkinLabel: RobotSkinLabel = getSpineSkinLabel(labelTimes.getJSONObject(i))
                if (spineSkinLabel != null) {
                    labelList.add(spineSkinLabel)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return labelList
    }

    /**
     *
     * // TODO 需要更新代码
     * @param analogTimes
     * @return
     */
    private fun getAnalogTimes(analogTimes: JSONArray): List<RobotSkinAnalogTime> {
        val analogList: MutableList<RobotSkinAnalogTime> = ArrayList()

        for (i in 0 until analogTimes.length()) {
            try {
                val spineSkinAnalogTime: RobotSkinAnalogTime =
                    getSpineSkinAnalogTime(analogTimes.getJSONObject(i))
                if (spineSkinAnalogTime != null) {
                    analogList.add(spineSkinAnalogTime)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return analogList
    }

    private fun getSpineSkinAnalogTime(spineSkinAnalogTimeJsonObject: JSONObject): RobotSkinAnalogTime {
        val spineSkinAnalogTime: RobotSkinAnalogTime = RobotSkinAnalogTime()
        try {
            val analogTimeOrigRect: JSONObject? = getTargetJsonObject(
                spineSkinAnalogTimeJsonObject,
                ClockViewConsts.Companion.ORIG_RECT
            )
            if (analogTimeOrigRect != null) {
                val rect: Rect? = getRect(analogTimeOrigRect)
                if (rect != null) {
                    spineSkinAnalogTime.setOrigRect(rect)
                }
            }

            val filePrefix: String? = getTargetStringValue(
                spineSkinAnalogTimeJsonObject,
                ClockViewConsts.Companion.FILE_PREFIX
            )
            if (filePrefix != null) {
                spineSkinAnalogTime.setFilePrefix(filePrefix)
            }

            val origHourAnchor: JSONObject? = getTargetJsonObject(
                spineSkinAnalogTimeJsonObject,
                ClockViewConsts.Companion.ORIG_HOUR_ANCHOR
            )
            if (origHourAnchor != null) {
                val hourPoint: Point? = getPoint(origHourAnchor)
                if (hourPoint != null) {
                    spineSkinAnalogTime.setOrigHourAnchor(hourPoint)
                }
            }

            val origMinuteAnchor: JSONObject? = getTargetJsonObject(
                spineSkinAnalogTimeJsonObject,
                ClockViewConsts.Companion.ORIG_MINUTE_ANCHOR
            )
            if (origMinuteAnchor != null) {
                val minutePoint: Point? = getPoint(origMinuteAnchor)
                if (minutePoint != null) {
                    spineSkinAnalogTime.setOrigMinuteAnchor(minutePoint)
                }
            }

            val origSecondAnchor: JSONObject? = getTargetJsonObject(
                spineSkinAnalogTimeJsonObject,
                ClockViewConsts.Companion.ORIG_SECOND_ANCHOR
            )
            if (origSecondAnchor != null) {
                val secondPoint: Point? = getPoint(origSecondAnchor)
                if (secondPoint != null) {
                    spineSkinAnalogTime.setOrigSecondAnchor(secondPoint)
                }
            }
        } catch (e: Exception) {
        }

        return spineSkinAnalogTime
    }


    /**
     * 获取对应的 SpineSkinLabel 对象
     *
     * @param spineSkinLabelJSONObject
     * @return
     */
    private fun getSpineSkinLabel(spineSkinLabelJSONObject: JSONObject): RobotSkinLabel {
        val spineSkinLabel: RobotSkinLabel = RobotSkinLabel()

        try {
            val digitTimesOrigRect: JSONObject? =
                getTargetJsonObject(spineSkinLabelJSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (digitTimesOrigRect != null) {
                val rect: Rect? = getRect(digitTimesOrigRect)
                if (rect != null) {
                    spineSkinLabel.setOrigRect(rect)
                }
            }

            val align: Int =
                getTargetIntValue(spineSkinLabelJSONObject, ClockViewConsts.Companion.ALIGN)
            spineSkinLabel.setAlign(align)

            val dataFormat: String? = getTargetStringValue(
                spineSkinLabelJSONObject,
                ClockViewConsts.Companion.DATA_FORMAT
            )
            if (dataFormat != null) {
                spineSkinLabel.setDataFormat(dataFormat)
            }

            val origSize: Int =
                getTargetIntValue(spineSkinLabelJSONObject, ClockViewConsts.Companion.ORIG_SIZE)
            spineSkinLabel.setOrigSize(origSize)

            val languageFormat: String? = getTargetStringValue(
                spineSkinLabelJSONObject,
                ClockViewConsts.Companion.LANGUAGE_FORMAT
            )
            if (languageFormat != null) {
                spineSkinLabel.setLaunguageFormat(languageFormat)
            }
            val color: Int =
                getTargetIntValue(spineSkinLabelJSONObject, ClockViewConsts.Companion.COLOR)
            spineSkinLabel.setColor(color)

            val style: Int =
                getTargetIntValue(spineSkinLabelJSONObject, ClockViewConsts.Companion.STYLE)
            spineSkinLabel.setStyle(style)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spineSkinLabel
    }

    /**
     * 获取对应的 SpineSkinShape 对象
     *
     * @param spineSkinShapeJSONObject
     * @return
     */
    private fun getSpineSkinShape(spineSkinShapeJSONObject: JSONObject): RobotSkinShape {
        val spineSkinShape: RobotSkinShape = RobotSkinShape()

        try {
            val align: Int =
                getTargetIntValue(spineSkinShapeJSONObject, ClockViewConsts.Companion.ALIGN)
            spineSkinShape.setAlign(align)

            val bgColor: Int =
                getTargetIntValue(spineSkinShapeJSONObject, ClockViewConsts.Companion.BG_COLOR)
            spineSkinShape.setBgColor(bgColor)

            val fgColor: Int =
                getTargetIntValue(spineSkinShapeJSONObject, ClockViewConsts.Companion.FG_COLOR)
            spineSkinShape.setFgColor(fgColor)

            val digitTimesOrigRect: JSONObject? =
                getTargetJsonObject(spineSkinShapeJSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (digitTimesOrigRect != null) {
                val rect: Rect? = getRect(digitTimesOrigRect)
                if (rect != null) {
                    spineSkinShape.setOrigRect(rect)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spineSkinShape
    }

    /**
     * 获取对应的 SpineSkinArc 对象
     *
     * @param spineSkinArcJSONObject
     * @return
     */
    private fun getSpineSkinArc(spineSkinArcJSONObject: JSONObject): RobotSkinArc {
        val spineSkinArc: RobotSkinArc = RobotSkinArc()

        try {
            val align: Int =
                getTargetIntValue(spineSkinArcJSONObject, ClockViewConsts.Companion.ALIGN)
            val color: Int =
                getTargetIntValue(spineSkinArcJSONObject, ClockViewConsts.Companion.COLOR)
            val strokeWidth: Int =
                getTargetIntValue(spineSkinArcJSONObject, ClockViewConsts.Companion.STROKE_WIDTH)
            val startAngle: Int =
                getTargetIntValue(spineSkinArcJSONObject, ClockViewConsts.Companion.START_ANGLE)
            val sweepAngle: Int =
                getTargetIntValue(spineSkinArcJSONObject, ClockViewConsts.Companion.SWEEP_ANGLE)

            val spineSkinArcOrigRect: JSONObject? =
                getTargetJsonObject(spineSkinArcJSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (spineSkinArcOrigRect != null) {
                val rect: Rect? = getRect(spineSkinArcOrigRect)
                if (rect != null) {
                    spineSkinArc.setOrigRect(rect)
                }
            }

            spineSkinArc.setAlign(align)
            spineSkinArc.setColor(color)
            spineSkinArc.setStrokeWidth(strokeWidth)
            spineSkinArc.setStartAngle(startAngle)
            spineSkinArc.setSweepAngle(sweepAngle)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spineSkinArc
    }

    /**
     * 获取对应的 SpineSkinAnchor 对象
     *
     * @param spineSkinAnchorJSONObject
     */
    private fun getSpineSkinAnchor(spineSkinAnchorJSONObject: JSONObject): RobotSkinAnchor {
        val spineSkinAnchor: RobotSkinAnchor = RobotSkinAnchor()

        try {
            val origRect: JSONObject? =
                getTargetJsonObject(spineSkinAnchorJSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (origRect != null) {
                val bgRect: Rect? = getRect(origRect)
                if (bgRect != null) {
                    spineSkinAnchor.setOrigRect(bgRect)
                }
            }

            val origAnchor: JSONObject? = getTargetJsonObject(
                spineSkinAnchorJSONObject,
                ClockViewConsts.Companion.ORIG_ANCHOR
            )
            if (origAnchor != null) {
                val oaPoint: Point? = getPoint(origAnchor)
                if (oaPoint != null) {
                    spineSkinAnchor.setOrigAnchor(oaPoint)
                }
            }

            val imgFile: String? =
                getTargetStringValue(spineSkinAnchorJSONObject, ClockViewConsts.Companion.IMG_FILE)
            if (imgFile != null) {
                spineSkinAnchor.setImgFile(imgFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spineSkinAnchor
    }

    /**
     * 获取对应的 SpineSkinImage 对象
     * @param spineSkinImageJSONObject
     */
    private fun getSpineSkinImage(spineSkinImageJSONObject: JSONObject): RobotSkinImage {
        val spineSkinImage: RobotSkinImage = RobotSkinImage()

        try {
            val origRect: JSONObject? =
                getTargetJsonObject(spineSkinImageJSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (origRect != null) {
                val bgRect: Rect? = getRect(origRect)
                if (bgRect != null) {
                    spineSkinImage.setOrigRect(bgRect)
                }
            }

            val align: Int =
                getTargetIntValue(spineSkinImageJSONObject, ClockViewConsts.Companion.ALIGN)
            spineSkinImage.setAlign(align)

            val filePrefix: String? = getTargetStringValue(
                spineSkinImageJSONObject,
                ClockViewConsts.Companion.FILE_PREFIX
            )
            if (filePrefix != null) {
                spineSkinImage.setFilePrefix(filePrefix)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spineSkinImage
    }

    /**
     * 获取对应的 SpineSkinImageWithSpace 对象
     * @param spineSkinImageWithSpaceJSONObject
     */
    private fun getSpineSkinImageWithSpace(spineSkinImageWithSpaceJSONObject: JSONObject): RobotSkinImageWithSpace {
        val spineSkinImageWithSpace: RobotSkinImageWithSpace = RobotSkinImageWithSpace()

        try {
            val origRect: JSONObject? = getTargetJsonObject(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.ORIG_RECT
            )
            if (origRect != null) {
                val bgRect: Rect? = getRect(origRect)
                if (bgRect != null) {
                    spineSkinImageWithSpace.setOrigRect(bgRect)
                }
            }

            val align: Int = getTargetIntValue(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.ALIGN
            )
            spineSkinImageWithSpace.setAlign(align)

            val aligns: Int = getTargetIntValue(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.ALIGNS
            )
            spineSkinImageWithSpace.setAligns(aligns)

            val total: Int = getTargetIntValue(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.TOTAL
            )
            spineSkinImageWithSpace.setTotal(total)

            val fileSpace: Int = getTargetIntValue(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.FILE_SPACE
            )
            spineSkinImageWithSpace.setFileSpace(fileSpace)

            val filePrefix: String? = getTargetStringValue(
                spineSkinImageWithSpaceJSONObject,
                ClockViewConsts.Companion.FILE_PREFIX
            )
            if (filePrefix != null) {
                spineSkinImageWithSpace.setFilePrefix(filePrefix)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spineSkinImageWithSpace
    }

    /**
     * 获取对应的 SpineSkinImageWithSpace 对象
     * @param spineSkinImageWithAngleJSONObject
     */
    private fun getSpineSkinImageWithAngle(spineSkinImageWithAngleJSONObject: JSONObject): RobotSkinImageWithAngle {
        val spineSkinImageWithAngle: RobotSkinImageWithAngle = RobotSkinImageWithAngle()

        try {
            val origAnchor: JSONObject? = getTargetJsonObject(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.ORIG_ANCHOR
            )
            if (origAnchor != null) {
                val oaPoint: Point? = getPoint(origAnchor)
                if (oaPoint != null) {
                    spineSkinImageWithAngle.setOrigAnchor(oaPoint)
                }
            }

            val displayAnchor: JSONObject? = getTargetJsonObject(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.DISPLAY_ANCHOR
            )
            if (displayAnchor != null) {
                val daPoint: Point? = getPoint(displayAnchor)
                if (daPoint != null) {
                    spineSkinImageWithAngle.setDisplayAnchor(daPoint)
                }
            }

            val align: Int = getTargetIntValue(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.ALIGN
            )
            spineSkinImageWithAngle.setAlign(align)

            val total: Int = getTargetIntValue(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.TOTAL
            )
            spineSkinImageWithAngle.setTotal(total)

            val intervalAngle: Int = getTargetIntValue(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.INTERVAL_ANGLE
            )
            spineSkinImageWithAngle.setIntervalAngle(intervalAngle)

            val imageAngle: Int = getTargetIntValue(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.IMAGE_ANGLE
            )
            spineSkinImageWithAngle.setImageAngle(imageAngle)

            val startAngle: Int = getTargetIntValue(
                spineSkinImageWithAngleJSONObject,
                ClockViewConsts.Companion.START_ANGLE
            )
            spineSkinImageWithAngle.setStartAngle(startAngle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spineSkinImageWithAngle
    }


    /**
     * 获取对应的 SpineSkinNumber 对象
     * @param spineSkinNumberSONObject
     */
    private fun getSpineSkinNumber(spineSkinNumberSONObject: JSONObject): RobotSkinNumber {
        val spineSkinNumber: RobotSkinNumber = RobotSkinNumber()

        try {
            val origRect: JSONObject? =
                getTargetJsonObject(spineSkinNumberSONObject, ClockViewConsts.Companion.ORIG_RECT)
            if (origRect != null) {
                val bgRect: Rect? = getRect(origRect)
                if (bgRect != null) {
                    spineSkinNumber.setOrigRect(bgRect)
                }
            }

            val align: Int =
                getTargetIntValue(spineSkinNumberSONObject, ClockViewConsts.Companion.ALIGN)
            spineSkinNumber.setAlign(align)

            val filePrefix: String? = getTargetStringValue(
                spineSkinNumberSONObject,
                ClockViewConsts.Companion.FILE_PREFIX
            )
            if (filePrefix != null) {
                spineSkinNumber.setFilePrefix(filePrefix)
            }
            val dataFormat: String? = getTargetStringValue(
                spineSkinNumberSONObject,
                ClockViewConsts.Companion.DATA_FORMAT
            )
            if (filePrefix != null) {
                spineSkinNumber.setDataFormat(dataFormat)
            }

            val fileSpace: Int =
                getTargetIntValue(spineSkinNumberSONObject, ClockViewConsts.Companion.FILE_SPACE)
            spineSkinNumber.setFileSpace(fileSpace)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spineSkinNumber
    }

    /**
     * 获取对应的rect
     * @param origRect
     * @return
     */
    private fun getRect(origRect: JSONObject): Rect? {
        val rect: Rect = Rect()
        try {
            val left: Int = origRect.getInt(ClockViewConsts.Companion.LEFT)
            val top: Int = origRect.getInt(ClockViewConsts.Companion.TOP)
            val right: Int = origRect.getInt(ClockViewConsts.Companion.RIGHT)
            val bottom: Int = origRect.getInt(ClockViewConsts.Companion.BOTTOM)
            rect.set(left, top, right, bottom)
        } catch (e: JSONException) {
            return null
        }
        return rect
    }

    /**
     * 获取对应的 Point
     * @param origAnchor
     * @return
     */
    private fun getPoint(origAnchor: JSONObject): Point? {
        val point: Point = Point()
        try {
            val x: Int = origAnchor.getInt(ClockViewConsts.Companion.X)
            val y: Int = origAnchor.getInt(ClockViewConsts.Companion.Y)
            point.set(x, y)
        } catch (e: JSONException) {
            return null
        }
        return point
    }
}
