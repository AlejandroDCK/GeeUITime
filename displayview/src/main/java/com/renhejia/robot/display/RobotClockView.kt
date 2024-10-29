package com.renhejia.robot.display

import android.content.Context
import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.PaintFlagsDrawFilter
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Handler
import android.provider.Settings
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.renhejia.robot.display.RobotClockView
import com.renhejia.robot.display.manager.RobotClockConfigManager
import com.renhejia.robot.display.utils.BitmapUtil
import com.renhejia.robot.display.utils.SpineSkinUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class RobotClockView : View, RobotPlatformListener {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mResPool: SpineSkinResPool? = null
    private var mSkin: RobotClockSkin? = null
    private var mPaintFlagsDrawFilter: PaintFlagsDrawFilter? = null
    private var mPlatformState: RobotPlatformState? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mContext: Context? = null
    private var isRefreshNow: Boolean = false //
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    private val mThread: Thread? = null

    var hourFormat: Int = SPINE_CLOCK_HOURS_24

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
        mPlatformState = RobotPlatformState.getInstance(context)

        //[niu][20191211]Format 12/24 hour time base on system setting
        hourFormat =
            if (DateFormat.is24HourFormat(context)) SPINE_CLOCK_HOURS_24 else SPINE_CLOCK_HOURS_12

        //        Timer timer = new Timer();
//        Calendar calendar = Calendar.getInstance();
//        int second = calendar.get(Calendar.SECOND);
        mPaintFlagsDrawFilter =
            PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        mPaint.setFakeBoldText(true)
        mPaint.setAntiAlias(true)
        //
//        Calendar calendar = Calendar.getInstance();
//        int second = calendar.get(Calendar.SECOND);
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                postInvalidate();
//            }
//        }, (60 - second) * 1000, 1000 * 60);
    }

    var mSettingsObserver: ContentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            //            LogUtil.d(TAG, "is24HourFormat onChanged:" + Settings.System.getString(getContext().getContentResolver(), Settings.System.TIME_12_24));
            hourFormat =
                if (DateFormat.is24HourFormat(context)) SPINE_CLOCK_HOURS_24 else SPINE_CLOCK_HOURS_12
            //this.mHourFormat = this.hourFormat

            postInvalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //        LogUtil.d(TAG, "registerContentObserver.....");
        getContext().getContentResolver().registerContentObserver(
            Settings.System.getUriFor(Settings.System.TIME_12_24),
            true,
            mSettingsObserver
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        //        LogUtil.d(TAG, "unregisterContentObserver.....");
        getContext().getContentResolver().unregisterContentObserver(mSettingsObserver)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measureWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = measureWidth
        mHeight = measureHeight
        if (mSkin != null) {
            mSkin!!.resize(Rect(0, 0, mWidth, mHeight))
        }
    }

    fun setSkin(skin: RobotClockSkin) {
        mResPool = skin.resPool
        mSkin = skin
        if (mWidth != 0 && mHeight != 0) {
            mSkin!!.resize(Rect(0, 0, mWidth, mHeight))
        }
    }

    private fun drawStep(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val step: RobotSkinNumber? = mSkin!!.step
        if (step != null) {
            val stepString: String = mPlatformState!!.getStepNumber().toString()
            drawNumbers(canvas, step, stepString)
        }
    }

    private fun drawAirTemp(canvas: Canvas) {
        if (mSkin == null) {
            return
        }
        val airTemp = mSkin!!.airTemp
        if (airTemp != null) {
            var tempString: String = "--c"
            if (mPlatformState!!.getWeatherState() != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
//                tempString = mPlatformState.currentTemp + "c";
//                tempString = mPlatformState.getCurrentTemp() + "°";
                tempString = mPlatformState!!.getCurrentTempString()
            }

            drawNumbers(canvas, airTemp, tempString)
        }
    }

    private fun drawWeekAnchor(canvas: Canvas, date: Date) {
        if (mSkin == null) {
            return
        }

        val anchor = mSkin!!.weekAnchor
        if (anchor != null) {
            val now: Calendar = Calendar.getInstance()
            now.setTime(date)
            // boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
            val weekDay: Int = now.get(Calendar.DAY_OF_WEEK)
            var weekId: Int = 0
            when (weekDay) {
                Calendar.SUNDAY -> weekId = 7
                Calendar.MONDAY -> weekId = 1
                Calendar.TUESDAY -> weekId = 2
                Calendar.WEDNESDAY -> weekId = 3
                Calendar.THURSDAY -> weekId = 4
                Calendar.FRIDAY -> weekId = 5
                Calendar.SATURDAY -> weekId = 6
                else -> {}
            }

            val bitmap: Bitmap? = mResPool!!.getBitmap(
                anchor.getImgFile()!!,
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                val matrix1: Matrix = Matrix()
                val imgAnchor: Point? = anchor.getDispAnchor()
                val displayPoint: Point = Point(
                    anchor.getDispRect()!!.centerX(), anchor.getDispRect()!!.centerY()
                )
                matrix1.postTranslate(
                    (displayPoint.x - imgAnchor!!.x).toFloat(),
                    (displayPoint.y - imgAnchor.y).toFloat()
                )
                matrix1.postRotate(
                    (weekId * 360 / 7).toFloat(),
                    displayPoint.x.toFloat(),
                    displayPoint.y.toFloat()
                )
                canvas.drawBitmap(bitmap, matrix1, mPaint)
            }
        }
    }

    private fun drawBatteryAnchor(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val anchor = mSkin!!.batteryAnchor
        if (anchor != null) {
            val batteryLevel: Int = mPlatformState!!.batteryLevel

            val imgAnchor: Point?
            val displayPoint: Point = Point(
                anchor.getDispRect()!!.centerX(), anchor.getDispRect()!!.centerY()
            )

            val bitmap: Bitmap? = mResPool!!.getBitmap(
                anchor.getImgFile()!!,
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )

            if (bitmap != null) {
                val matrix1: Matrix = Matrix()
                imgAnchor = anchor.getDispAnchor()
                matrix1.postTranslate(
                    (displayPoint.x - imgAnchor!!.x).toFloat(),
                    (displayPoint.y - imgAnchor.y).toFloat()
                )
                matrix1.postRotate(
                    (batteryLevel * 33 / 10).toFloat(),
                    displayPoint.x.toFloat(),
                    displayPoint.y.toFloat()
                )
                canvas.drawBitmap(bitmap, matrix1, mPaint)
            }
        }
    }

    private fun drawRotateBackground(canvas: Canvas, rotateBg: RobotSkinAnchor?) {
        if (mSkin == null) {
            return
        }

        //TODO 背景选装
        if (rotateBg != null && rotateBg.getDispRect() != null) {
            val imgAnchor: Point?
            val displayPoint: Point = Point(
                rotateBg.getDispRect()!!.centerX(), rotateBg.getDispRect()!!.centerY()
            )

            var bitmap: Bitmap? = mResPool!!.getBitmap(
                rotateBg.getImgFile()!!,
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            bitmap = rsBlur(mContext, bitmap, 10f, 1f)
            val cal: Calendar = Calendar.getInstance()
            cal.setTime(Date(System.currentTimeMillis()))
            val nSecond: Int = cal.get(Calendar.SECOND)
            val mMillisecond: Int = cal.get(Calendar.MILLISECOND)
            if (bitmap != null) {
                val matrix1: Matrix = Matrix()
                imgAnchor = rotateBg.getDispAnchor()
                if (imgAnchor == null) {
//                    LogUtils.logi("testFileName", "imgAnchor is null");
                }
                matrix1.postTranslate(
                    (displayPoint.x - imgAnchor!!.x).toFloat(),
                    (displayPoint.y - imgAnchor.y).toFloat()
                )
                //                matrix1.postRotate((nSecond % 8)*45+ (int)(mMillisecond * 45/1000), displayPoint.x, displayPoint.y);
                matrix1.postRotate(
                    ((nSecond % 15) * 24 + (mMillisecond * 24 / 1000)).toFloat(),
                    displayPoint.x.toFloat(),
                    displayPoint.y.toFloat()
                )
                //                matrix1.postRotate(nSecond*6, displayPoint.x, displayPoint.y);
                canvas.drawBitmap(bitmap, matrix1, mPaint)
            }
        }
    }

    private fun drawNumbers(canvas: Canvas, numbers: RobotSkinNumber, text: String) {
        if (mSkin == null) {
            return
        }
        //        LogUtils.logi("testFileName","drawAirTemp:======= 6 ");
        val align: Int = numbers.getAlign()

        //        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(numbers.getDispRect(), mPaint);
        if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_TOP)) {
            val top: Float = numbers.getDispRect()!!.top.toFloat()
            var left: Float = numbers.getDispRect()!!.left.toFloat()

            val calendar: Calendar = Calendar.getInstance()
            val millisecond: Int = calendar.get(Calendar.MILLISECOND)

            for (i in 0 until text.length) {
                val subTime: String = text.substring(i, i + 1)
                val bitmap: Bitmap? = mResPool!!.getBitmap(
                    numbers.getImgFilename(subTime),
                    mSkin!!.getxRadio(),
                    mSkin!!.getyRadio()
                )
                //                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, left, top, mPaint)
                    left += numbers.getFileSpace().toFloat()
                    left += bitmap.getWidth().toFloat()
                }
            }
        } else {
            var width: Int = 0
            var height: Int = 0
            if (numbers == null) {
//                LogUtils.logi("robot_exceptipn", "numbers is null");
            } else if (numbers.getDispRect() == null) {
//                LogUtils.logi("robot_exceptipn", "numbers.getDispRect() is null");
            } else if (numbers.getDispRect()!!.top == 0) {
//                LogUtils.logi("robot_exceptipn", "numbers.getDispRect() is null === 0");
            }
            if (numbers == null || numbers.getDispRect() == null) {
                return
            }
            var top: Int = numbers.getDispRect()!!.top
            var left: Int = numbers.getDispRect()!!.left

            for (i in 0 until text.length) {
                val subTime: String = text.substring(i, i + 1)
                val calendar: Calendar = Calendar.getInstance()
                val millisecond: Int = calendar.get(Calendar.MILLISECOND)
                val bitmap: Bitmap? = mResPool!!.getBitmap(
                    numbers.getImgFilename(subTime),
                    mSkin!!.getxRadio(),
                    mSkin!!.getyRadio()
                )
                //                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    width += numbers.getFileSpace()
                    width += bitmap.getWidth()

                    if (bitmap.getHeight() > height) {
                        height = bitmap.getHeight()
                    }
                }
            }

            if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_TOP)) {
                left = numbers.getDispRect()!!.left + (numbers.getDispRect()!!.width() - width) / 2
                top = numbers.getDispRect()!!.top
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_TOP)) {
                left = numbers.getDispRect()!!.right - width
                top = numbers.getDispRect()!!.top
            } else if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect()!!.left
                top = numbers.getDispRect()!!.top + (numbers.getDispRect()!!.height() - height) / 2
            } else if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect()!!.left + (numbers.getDispRect()!!.width() - width) / 2
                top = numbers.getDispRect()!!.top + (numbers.getDispRect()!!.height() - height) / 2
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_MIDDLE)) {
                left = numbers.getDispRect()!!.left + numbers.getDispRect()!!.width() - width
                top = numbers.getDispRect()!!.top + (numbers.getDispRect()!!.height() - height) / 2
            } else if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect()!!.left
                top = numbers.getDispRect()!!.top + numbers.getDispRect()!!.height() - height
            } else if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect()!!.left + (numbers.getDispRect()!!.width() - width) / 2
                top = numbers.getDispRect()!!.top + numbers.getDispRect()!!.height() - height
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_BOTTOM)) {
                left = numbers.getDispRect()!!.left + numbers.getDispRect()!!.width() - width
                top = numbers.getDispRect()!!.top + numbers.getDispRect()!!.height() - height
            }
            val calendar: Calendar = Calendar.getInstance()
            val millisecond: Int = calendar.get(Calendar.MILLISECOND)
            for (i in 0 until text.length) {
                val subTime: String = text.substring(i, i + 1)
                val bitmap: Bitmap? = mResPool!!.getBitmap(
                    numbers.getImgFilename(subTime),
                    mSkin!!.getxRadio(),
                    mSkin!!.getyRadio()
                )
                //                Bitmap bitmap = mResPool.getBitmap(numbers.getImgFilenameFlash(subTime,millisecond), mSkin.getxRadio(), mSkin.getyRadio());
//                if (bitmap == null) {
//                    bitmap = mResPool.getBitmap(numbers.getImgFilename(subTime), mSkin.getxRadio(), mSkin.getyRadio());
//                }
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, left.toFloat(), top.toFloat(), mPaint)
                    left += numbers.getFileSpace()
                    left += bitmap.getWidth()
                }
            }
        }
    }

    private fun drawDigitTime(canvas: Canvas, digitTime: RobotSkinNumber, date: Date) {
        val timeString: String? = digitTime.getTimeString(date, hourFormat)
        if (timeString != null && timeString.length > 0) {
            drawNumbers(canvas, digitTime, timeString)
        }
    }

    private fun drawAnalogTime(canvas: Canvas, analogTime: RobotSkinAnalogTime?, date: Date) {
        if (mSkin == null) {
            return
        }

        if (analogTime != null) {
            val cal: Calendar = Calendar.getInstance()
            cal.setTime(date)

            val nHour: Int = cal.get(Calendar.HOUR)
            val nMinute: Int = cal.get(Calendar.MINUTE)
            val nSecond: Int = cal.get(Calendar.SECOND)
            val nMilliSecond: Int = cal.get(Calendar.MILLISECOND)

            var imgAnchor: Point?
            val displayPoint: Point = Point(
                analogTime.getDispRect()!!.centerX(), analogTime.getDispRect()!!.centerY()
            )

            var bitmap: Bitmap? = mResPool!!.getBitmap(
                analogTime.getHourFilename(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )

            if (bitmap != null) {
                val matrix1: Matrix = Matrix()
                imgAnchor = analogTime.getDispHourAnchor()
                matrix1.postTranslate(
                    (displayPoint.x - imgAnchor!!.x).toFloat(),
                    (displayPoint.y - imgAnchor.y).toFloat()
                )
                matrix1.postRotate(
                    ((nHour * 3600 + nMinute * 60 + nSecond) * 360 / (12 * 3600)).toFloat(),
                    displayPoint.x.toFloat(),
                    displayPoint.y.toFloat()
                )
                canvas.drawBitmap(bitmap, matrix1, mPaint)
            }

            bitmap = mResPool!!.getBitmap(
                analogTime.getMinuteFilename(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )

            if (bitmap != null) {
                val matrix1: Matrix = Matrix()
                imgAnchor = analogTime.getDispMinuteAnchor()
                matrix1.postTranslate(
                    (displayPoint.x - imgAnchor!!.x).toFloat(),
                    (displayPoint.y - imgAnchor.y).toFloat()
                )
                matrix1.postRotate(
                    ((nMinute * 60 + nSecond) * 360 / (3600)).toFloat(),
                    displayPoint.x.toFloat(),
                    displayPoint.y.toFloat()
                )
                canvas.drawBitmap(bitmap, matrix1, mPaint)
            }

            if (analogTime.getDispSecondAnchor() != null) {
                bitmap = mResPool!!.getBitmap(
                    analogTime.getSecondFilename(),
                    mSkin!!.getxRadio(),
                    mSkin!!.getyRadio()
                )

                if (bitmap != null) {
                    val matrix1: Matrix = Matrix()
                    imgAnchor = analogTime.getDispSecondAnchor()
                    matrix1.postTranslate(
                        (displayPoint.x - imgAnchor!!.x).toFloat(),
                        (displayPoint.y - imgAnchor.y).toFloat()
                    )
                    matrix1.postRotate(
                        ((nSecond) * 360 / (60)).toFloat(),
                        displayPoint.x.toFloat(),
                        displayPoint.y.toFloat()
                    )
                    //                    matrix1.postRotate((nSecond) * 360 *5 / (60), displayPoint.x, displayPoint.y);
                    canvas.drawBitmap(bitmap, matrix1, mPaint)
                }
            }
        }
    }

    private fun drawBatteryAngle(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.batteryAngle
        if (icon != null) {
            val batteryLevel: Int = mPlatformState!!.batteryLevel
            val total: Int = icon.getTotal()
            if (total == 0) {
                return
            }
            val batteryVal: Int = (batteryLevel + (100 / (total * 2))) / (100 / total)

            val bitmapFull: Bitmap? = mResPool!!.getBitmap(
                icon.getBatteryFanFull(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            val bitmapEmpty: Bitmap? = mResPool!!.getBitmap(
                icon.getBatteryFanEmpty(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmapEmpty == null || bitmapFull == null) {
                return
            }

            if (bitmapFull != null) {
                val matrix1: Matrix = Matrix()
                val imgAnchor: Point? = icon.getDisplayAnchor()
                matrix1.postTranslate(
                    (icon.getOrigAnchor()!!.x - imgAnchor!!.x).toFloat(),
                    (icon.getOrigAnchor()!!.y - imgAnchor.y).toFloat()
                )
                for (i in 0 until total) {
                    if (i == 0) {
                        matrix1.postRotate(
                            icon.getStartAngle().toFloat(),
                            icon.getOrigAnchor()!!.x.toFloat(),
                            icon.getOrigAnchor()!!.y.toFloat()
                        )
                    } else {
                        matrix1.postRotate(
                            (icon.getImageAngle() + icon.getIntervalAngle()).toFloat(),
                            icon.getOrigAnchor()!!.x.toFloat(),
                            icon.getOrigAnchor()!!.y.toFloat()
                        )
                    }

                    if (i < batteryVal) {
                        canvas.drawBitmap(bitmapFull, matrix1, mPaint)
                    } else {
                        canvas.drawBitmap(bitmapEmpty, matrix1, mPaint)
                    }
                }
            }
        }
    }

    private fun drawBatterySquares(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.batterySquares
        if (icon != null) {
            val batteryLevel: Int = mPlatformState!!.batteryLevel
            val total: Int = icon.getTotal()
            if (total == 0) {
                return
            }
            val batteryVal: Int = (batteryLevel + (100 / (total * 2))) / (100 / total)

            val bitmapFull: Bitmap? = mResPool!!.getBitmap(
                icon.getBatterySquaresFull(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            val bitmapEmpty: Bitmap? = mResPool!!.getBitmap(
                icon.getBatterySquaresEmpty(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            drawSquares(canvas, icon, bitmapFull, bitmapEmpty, total, batteryVal)
        }
    }

    private fun drawStepSquares(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val stepIcon = mSkin!!.stepSquares
        if (stepIcon != null) {
            val stepNum: Int = mPlatformState!!.getStepNumber()
            val total: Int = stepIcon.getTotal()
            if (total == 0) {
                return
            }
            var stepLevel: Int = stepNum / 1000
            if (stepLevel > total) {
                stepLevel = total
            }
            val bitmapFull: Bitmap? = mResPool!!.getBitmap(
                stepIcon.getStepSquaresFull(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            val bitmapEmpty: Bitmap? = mResPool!!.getBitmap(
                stepIcon.getStepSquaresEmpty(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            drawSquares(canvas, stepIcon, bitmapFull, bitmapEmpty, total, stepLevel)
        }
    }

    private fun drawSquares(
        canvas: Canvas,
        icon: RobotSkinImageWithSpace?,
        bitmapFull: Bitmap?,
        bitmapEmpty: Bitmap?,
        total: Int,
        currentLevel: Int
    ) {
        var displayX: Int = 0
        var displayY: Int = 0
        if (icon != null) {
            if (total == 0) {
                return
            }

            if ((bitmapEmpty != null) && (bitmapFull != null)) {
                displayX = icon.getDispRect()!!.left
                displayY = icon.getDispRect()!!.top

                if (currentLevel >= 0) {
                    for (j in 0 until currentLevel) {
                        canvas.drawBitmap(
                            bitmapFull,
                            displayX.toFloat(),
                            displayY.toFloat(),
                            mPaint
                        )
                        if (icon.getAligns() == RobotClockSkin.ALIGN_LEFT_TO_RIGHT) {
                            displayX += bitmapFull.getWidth() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_RIGHT_TO_LEFT) {
                            displayX -= bitmapFull.getWidth() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_TOP_TO_BOTTOM) {
                            displayY += bitmapFull.getHeight() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_BOTTOM_TO_TOP) {
                            displayY -= bitmapFull.getHeight() + +icon.getFileSpace()
                        }
                    }

                    for (i in 0 until total - currentLevel) {
                        canvas.drawBitmap(
                            bitmapEmpty,
                            displayX.toFloat(),
                            displayY.toFloat(),
                            mPaint
                        )
                        if (icon.getAligns() == RobotClockSkin.ALIGN_LEFT_TO_RIGHT) {
                            displayX += bitmapEmpty.getWidth() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_RIGHT_TO_LEFT) {
                            displayX -= bitmapEmpty.getWidth() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_TOP_TO_BOTTOM) {
                            displayY += bitmapEmpty.getHeight() + icon.getFileSpace()
                        } else if (icon.getAligns() == RobotClockSkin.ALIGN_BOTTOM_TO_TOP) {
                            displayY -= bitmapEmpty.getHeight() + icon.getFileSpace()
                        }
                    }
                }
            }
        }
    }


    private fun drawStepSkinArc(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val stepSkinArc = mSkin!!.stepSkinArc

        if (stepSkinArc != null) {
            val stepCount: Int = mPlatformState!!.getStepNumber()
            var stepProgress: Int = stepCount / 100
            if (stepProgress >= 100) {
                stepProgress = 100
            }
            val paint: Paint = Paint()
            paint.setAntiAlias(true)
            paint.setStyle(Paint.Style.STROKE)
            paint.setStrokeCap(Paint.Cap.ROUND)
            if (stepProgress > 0) {
                paint.setColor(stepSkinArc.getColor())
                paint.setStrokeWidth(stepSkinArc.getStrokeWidth())
                canvas.drawArc(
                    RectF(stepSkinArc.getDispRect()),
                    stepSkinArc.getStartAngle().toFloat(),
                    (stepProgress.toFloat() / 100) * stepSkinArc.getSweepAngle(),
                    false,
                    paint
                )
            }
        }
    }


    private fun drawBatteryProgressBar(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val batteryProgress = mSkin!!.batteryProgress

        if (batteryProgress != null) {
            val batteryLevel: Int = mPlatformState!!.batteryLevel
            val currentProgress: Int =
                (batteryProgress.getDispRect()!!.right - batteryProgress.getDispRect()!!.left) * batteryLevel / 100
            val backgroundPaint: Paint = Paint()
            backgroundPaint.setAntiAlias(true)
            backgroundPaint.setColor(batteryProgress.getBgColor())
            canvas.drawRect(batteryProgress.getDispRect()!!, backgroundPaint)

            val foregroundPaint: Paint = Paint()
            foregroundPaint.setAntiAlias(true)
            foregroundPaint.setColor(batteryProgress.getFgColor())
            canvas.drawRect(
                Rect(
                    batteryProgress.getDispRect()!!.left,
                    batteryProgress.getDispRect()!!.top,
                    batteryProgress.getDispRect()!!.left + currentProgress,
                    batteryProgress.getDispRect()!!.bottom
                ), foregroundPaint
            )
        }
    }

    private fun drawBattery(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.battery
        if (icon != null) {
            val batteryLevel: Int = mPlatformState!!.batteryLevel
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getBatteryFilename(batteryLevel),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawWifi(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.wifi
        if (icon != null) {
            val wifiEnable: Boolean = mPlatformState!!.isWifiEnabled()
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getOnOffFilename(wifiEnable),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawBluetooth(canvas: Canvas) {
        if (mSkin == null) {
            return
        }
        val icon = mSkin!!.bluetooth
        if (icon != null) {
            val bluetoothEnable: Boolean = mPlatformState!!.isBluetoothEnabled()
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getOnOffFilename(bluetoothEnable),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawCharge(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.charge
        if (icon != null) {
            val isCharging: Boolean = mPlatformState!!.isBatteryCharging()

            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getOnOffFilename(isCharging),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawWeather(canvas: Canvas) {
        if (mSkin == null) {
            return
        }
        if (!RobotClockConfigManager.getInstance(mContext)!!.IsShowWeather()) {
            return
        }

        val icon = mSkin!!.weather
        if (icon != null) {
            val weatherId: Int = mPlatformState!!.getWeatherState()
            if (weatherId != RobotPlatformState.NO_WEATHER) {
                val bitmap: Bitmap? = mResPool!!.getBitmap(
                    icon.getWeatherFilename(weatherId),
                    mSkin!!.getxRadio(),
                    mSkin!!.getyRadio()
                )
                if (bitmap != null && icon.getDispRect() != null) {
                    canvas.drawBitmap(
                        bitmap,
                        icon.getDispRect()!!.left.toFloat(),
                        icon.getDispRect()!!.top.toFloat(),
                        mPaint
                    )
                }
            }
        }
    }

    private fun drawNotices(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.notices
        if (icon != null) {
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getNoticeFileName(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null && icon.getDispRect() != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawFansIcon(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.fansIcon
        if (icon != null && icon.getDispRect() != null) {
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getFansIconFileName(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawFansHead(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.fansHead
        if (icon?.getDispRect() != null) {
            val bitmap = mResPool!!.getBitmap(
                icon.getFansHeadFileName(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawAqiNumber(canvas: Canvas) {
        if (mSkin == null) {
            return
        }


        val label = mSkin!!.aqiNumber
        if (label != null) {
            var aqiNumber = ""

            if (mPlatformState!!.getWeatherState() != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
                aqiNumber = mPlatformState!!.getAirQuality().toString()
            }

            drawLabel(canvas, label, aqiNumber)
        }
    }

    private fun drawStepNumber(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val label = mSkin!!.stepNumber
        if (label != null) {
            var stepNumber: String = ""
            stepNumber = mPlatformState!!.getStepNumber().toString()

            drawLabel(canvas, label, stepNumber)
        }
    }

    private fun drawBatteryNumber(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        if (mPlatformState!!.batteryLevel == 0) {
            return
        }
        val label = mSkin!!.batteryNumber
        if (label != null) {
            var batteryLevel: String = ""
            if ((label.getDataFormat() != null) && (label.getDataFormat() == NO_PERCENT_SIGN)) {
                batteryLevel = mPlatformState!!.batteryLevel.toString()
            } else {
                batteryLevel = mPlatformState!!.batteryLevel.toString() + "%"
            }

            drawLabel(canvas, label, batteryLevel)
        }
    }

    private fun drawTemperature(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val label = mSkin!!.temperature
        if (label != null) {
            var temperature: String? = ""
            if (mPlatformState!!.getCurrentTemp() == RobotPlatformState.NO_TEMP) {
//                temperature = "--" + "℃";
                temperature = "--"
                label.setColor(TEXT_COLOR_GARY)
            } else {
//                temperature = String.valueOf(mPlatformState.getCurrentTemp()) + "℃";
                temperature = mPlatformState!!.getCurrentTempString()
            }

            drawLabel(canvas, label, temperature)
        }
    }

    private fun drawTemperatureDes(canvas: Canvas) {
        if (mSkin == null) {
            return
        }
        if (!RobotClockConfigManager.getInstance(mContext)!!.IsShowWeather()) {
            return
        }
        val label = mSkin!!.temperatureRange
        if (label != null) {
            var temperatureDes: String? = ""
            temperatureDes = if (TextUtils.isEmpty(mPlatformState!!.getTempDes())) {
                "No weather information is available at this time."
            } else {
                mPlatformState!!.getTempDes()
            }
            if (RobotPlatformState.getInstance(mContext!!).getWeatherStateStr() != null) {
                drawLabel(canvas, label, temperatureDes)
            }
        }
    }

    private fun drawTemperatureInfo(canvas: Canvas) {
        if (mSkin == null) {
            return
        }
        if (!RobotClockConfigManager.getInstance(mContext)!!.IsShowWeather()) {
            return
        }
        val label: RobotSkinLabel? = mSkin!!.temperatureInfo
        if (label != null) {
            var temperatureInfo: String? = ""
            temperatureInfo = if (TextUtils.isEmpty(mPlatformState!!.getWeatherStateStr())) {
                "--"
            } else {
                mPlatformState!!.getWeatherStateStr()
            }
            if (RobotPlatformState.getInstance(mContext!!).getWeatherStateStr() != null) {
                drawLabel(canvas, label, temperatureInfo!!)
            }
        }
    }

    private fun drawTemperatureRange(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val label = mSkin!!.temperatureRange
        if (label != null) {
            val temperature: String? = mPlatformState!!.getTempRange()
            drawLabel(canvas, label, temperature!!)
        }
    }

    private fun drawLabel(canvas: Canvas, label: RobotSkinLabel, text: String) {
        mPaint.setTextSize(label.getDispSize().toFloat())
        mPaint.setColor(label.getColor())
        if (label.getStyle() == RobotClockSkin.STYLE_ITALIC) {
            mPaint.setTextSkewX(-0.25.toFloat())
        }

        val fontMetrics: FontMetricsInt = mPaint.getFontMetricsInt()
        val baseline: Int = fontMetrics.top + fontMetrics.descent

        val align: Int = label.getAlign()

        if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_TOP)) {
//            mPaint.setTextSkewX((float) -0.25);
            if (label != null && (label.getDispRect() != null)) {
                canvas.drawText(
                    text,
                    label.getDispRect()!!.left.toFloat(),
                    (label.getDispRect()!!.top - baseline).toFloat(),
                    mPaint
                )
            }
        } else {
            val minRect: Rect = Rect()
            mPaint.getTextBounds(text, 0, text.length, minRect)
            if (label == null || (label.getDispRect() == null)) {
                return
            }
            val topY: Int = label.getDispRect()!!.top - baseline
            val middleY: Int = label.getDispRect()!!.top + label.getDispRect()!!
                .height() / 2 - minRect.height() / 2 - baseline
            val bottomY: Int = label.getDispRect()!!.bottom - baseline - minRect.height()

            val centerX: Int =
                label.getDispRect()!!.left + (label.getDispRect()!!.width() - minRect.width()) / 2
            val leftX: Int = label.getDispRect()!!.left
            val rightX: Int = label.getDispRect()!!.right - minRect.width()

            if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_TOP)) {
                canvas.drawText(text, centerX.toFloat(), topY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_TOP)) {
                canvas.drawText(text, rightX.toFloat(), topY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, leftX.toFloat(), middleY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, centerX.toFloat(), middleY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_MIDDLE)) {
                canvas.drawText(text, rightX.toFloat(), middleY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_LEFT or RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, leftX.toFloat(), bottomY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_CENTER or RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, centerX.toFloat(), bottomY.toFloat(), mPaint)
            } else if (align == (RobotClockSkin.ALIGN_RIGHT or RobotClockSkin.ALIGN_BOTTOM)) {
                canvas.drawText(text, rightX.toFloat(), bottomY.toFloat(), mPaint)
            } else {
                canvas.drawText(text, leftX.toFloat(), topY.toFloat(), mPaint)
            }
        }
        mPaint.setTextSkewX(0f)
    }

    private fun getAqiText(aqiNumber: Int): String {
        if (aqiNumber < 51) {
            return "优"
        } else if (aqiNumber < 101) {
            return "良"
        } else if (aqiNumber < 151) {
            return "轻度污染"
        } else if (aqiNumber < 201) {
            return "中度污染"
        } else if (aqiNumber < 301) {
            return "重度污染"
        } else {
            return "严重污染"
        }
    }

    private fun drawAqiText(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val label: RobotSkinLabel? = mSkin!!.aqiText
        if (label != null) {
            var aqiText: String = ""
            if (mPlatformState!!.getWeatherState() != RobotClockSkin.WEATHER_TYPE_NO_INFO) {
                aqiText = getAqiText(mPlatformState!!.getAirQuality())
                ////                aqiText = "晴 26°  3个日历提醒";
//                aqiText = "晴 26°";
            }
            drawLabel(canvas, label, aqiText)
        }
    }

    private fun drawNoticeText(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val label = mSkin!!.noticeText
        if (label != null) {
            var noticeText = ""
            //TODO
            noticeText = " 1 calendar reminder"

            drawLabel(canvas, label, noticeText)
        }
    }

    private fun drawVolume(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val icon = mSkin!!.volume
        if (icon != null) {
            val volume: Int = mPlatformState!!.getMediaVolume()
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                icon.getVolumeFilename(volume),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    icon.getDispRect()!!.left.toFloat(),
                    icon.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    //    private void drawBackgroup(Canvas canvas) {
    //
    //        SpineSkinImage background = mSkin.getBackground();
    //        if (background != null) {
    //            Bitmap bitmap = mResPool.getBitmap(background.getBackgroundFilename());
    //            if (bitmap != null) {
    //                canvas.drawBitmap(bitmap, background.getOrigRect(), background.getDispRect(), mPaint);
    //            }
    //        }
    //    }
    private fun drawBackgroup(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val background = mSkin!!.background
        var bitmap: Bitmap?
        if (background != null) {
            if (SpineSkinUtils.isChangeSkinBackgroundExit(mContext!!, mResPool!!.getSkinPath())) {
                if (mResPool!!.getBitmap(background.getCustomizedBackgroundFilename()) == null) {
                    bitmap = BitmapUtil.convertImageToBitmap(
                        SpineSkinUtils.getFileNameOfChangeSkinBackground(
                            mContext!!, mResPool!!.getSkinPath()
                        )
                    )
                    mResPool!!.fillMapBitmap(background.getCustomizedBackgroundFilename(), bitmap)
                }
                bitmap = mResPool!!.getBitmap(background.getCustomizedBackgroundFilename())
            } else {
                bitmap = mResPool!!.getBitmap(background.getBackgroundFilename())
            }
            if (bitmap != null && background != null && background.getOrigRect() != null && background.getDispRect() != null) {
                canvas.drawBitmap(
                    bitmap, background.getOrigRect(),
                    background.getDispRect()!!, mPaint
                )
            }
        }
    }

    private fun drawForeground(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val foreground = mSkin!!.foreground
        if (foreground != null) {
            val bitmap: Bitmap? = mResPool!!.getBitmap(
                foreground.getForeground(),
                mSkin!!.getxRadio(),
                mSkin!!.getyRadio()
            )
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    foreground.getDispRect()!!.left.toFloat(),
                    foreground.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawMiddle(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val middle = mSkin!!.middle
        if (middle != null) {
            val bitmap: Bitmap? =
                mResPool!!.getBitmap(middle.getMiddle(), mSkin!!.getxRadio(), mSkin!!.getyRadio())
            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap,
                    middle.getDispRect()!!.left.toFloat(),
                    middle.getDispRect()!!.top.toFloat(),
                    mPaint
                )
            }
        }
    }

    private fun drawLabelTime(canvas: Canvas, label: RobotSkinLabel, date: Date, hourFormat: Int) {
        var strFormat: String? = label.getDataFormat()

        if (hourFormat == SPINE_CLOCK_HOURS_24) {
            strFormat = strFormat!!.replace("a", "")
            strFormat = strFormat.replace("hh", "HH")
        }

        if (strFormat!!.length > 0) {
            if (strFormat.length == 1) {
//                SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
//                String strDate = SpineSkinFileMap.getFilePostfix(format.format(date), label.getLanguageFormat());
////                mPaint.setTextSkewX((float) -0.25);
//                drawLabel(canvas, label, strDate);

                val format: SimpleDateFormat = SimpleDateFormat(strFormat, Locale.getDefault())

                val strDate: String? =
                    RobotSkinFileMap.getFilePostfix(format.format(date), label.getLanguageFormat())
                if ((Locale.getDefault().getLanguage()) == THAI || (Locale.getDefault()
                        .getLanguage()) == GERMAN
                ) {
                    if ((label.getLanguageFormat() != null) && (label.getLanguageFormat() == THAI)) {
                        drawLabel(canvas, label, strDate!!)
                    } else if (label.getLanguageFormat() == null) {
                        label.setDispSize(14)
                        drawLabel(canvas, label, strDate!!)
                    }
                } else if (!((label.getLanguageFormat() != null) && (label.getLanguageFormat() == THAI))) {
                    drawLabel(canvas, label, strDate!!)
                }
            } else {
                if (strFormat == YYYYMMDD_FORMAT || strFormat == MMDD_FORMAT) {
                    val format: SimpleDateFormat = SimpleDateFormat(strFormat, Locale.getDefault())
                    val strDate: String? = RobotSkinFileMap.getFilePostfix(format.format(date))
                    //                    mPaint.setTextSkewX((float) -0.25);
                    drawLabel(canvas, label, strDate!!)
                } else {
                    val strBestFormat: String =
                        DateFormat.getBestDateTimePattern(Locale.getDefault(), strFormat)
                    val strDate: String = DateFormat.format(strBestFormat, date).toString()
                    //                    mPaint.setTextSkewX((float) -0.25);
                    drawLabel(canvas, label, strDate)
                }
            }
        }
    }

    private fun drawTimes(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val date: Date = Date(System.currentTimeMillis())

        val digitTimes = mSkin!!.digitTimes
        if (digitTimes != null) {
            for (dt in digitTimes) {
                drawDigitTime(canvas, dt, date)
            }
        }

        drawWeekAnchor(canvas, date)

        drawMiddle(canvas)

        val labelTimes = mSkin!!.labelTimes
        if (labelTimes != null) {
            if (RobotClockConfigManager.getInstance(mContext)!!.IsShowDate()) {
                for (lt: RobotSkinLabel in labelTimes) {
                    drawLabelTime(canvas, lt, date, hourFormat)
                }
            }
        }

        val analogTimes = mSkin!!.analogTimes
        if (analogTimes != null) {
            for (at: RobotSkinAnalogTime? in analogTimes) {
                drawAnalogTime(canvas, at, date)
            }
        }
    }

    private fun drawCountdownEvent(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        //TODO Need to update data maintenance logic
        val events = mSkin!!.countdownEvent
        var eventText = ""

        if (events != null) {
            for (i in events.indices) {
                eventText = if (i == 0) {
                    "1 day later"
                } else {
                    "wedding anniversary"
                }
                drawLabel(canvas, events[i], eventText)
            }
        }
    }

    private fun drawBackgrounds(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        val backgrounds: List<RobotSkinAnchor?>? = mSkin!!.backgrounds

        if (backgrounds != null) {
            for (bg: RobotSkinAnchor? in backgrounds) {
//                LogUtils.logi("testFileName","bg.getImgFile(): "+ bg.getImgFile());
                drawRotateBackground(canvas, bg)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.setDrawFilter(mPaintFlagsDrawFilter)
        drawBackgroup(canvas)
        drawBackgrounds(canvas)
        drawBatteryProgressBar(canvas)
        drawBluetooth(canvas)
        drawWifi(canvas)
        drawVolume(canvas)
        drawBattery(canvas)
        drawStep(canvas)
        drawBatteryAnchor(canvas)
        drawAirTemp(canvas)
        drawWeather(canvas)
        drawCharge(canvas)
        drawAqiNumber(canvas)
        drawAqiText(canvas)
        drawStepNumber(canvas)
        drawBatteryNumber(canvas)
        drawTemperature(canvas)
        drawTemperatureInfo(canvas)
        drawTemperatureDes(canvas)
        drawStepSkinArc(canvas)
        drawBatterySquares(canvas)
        drawBatteryAngle(canvas)
        drawStepSquares(canvas)
        drawTimes(canvas)
        drawForeground(canvas)
        drawNotices(canvas)
        drawNotice(canvas)
        drawNoticeText(canvas)
        drawCountDownTimer(canvas)
        drawCountdownEvent(canvas)
        drawFansInfo(canvas)
        drawFansIcon(canvas)
        drawFansHead(canvas)
        drawTemperatureRange(canvas)
    }

    private fun drawCountDownTimer(canvas: Canvas) {
    }

    /**
     * 绘制提醒功能
     *
     * @param canvas
     */
    private fun drawNotice(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        //TODO 需要更新数据维护逻辑
        val notices = mSkin!!.notice
        var noticesText: String = ""

        if (notices != null) {
            for (i in notices.indices) {
                when (i) {
                    0 -> {
                        noticesText = "Today at 15:09"
                    }
                    1 -> {
                        noticesText = "Going to the supermarket to buy groceries, I'm going to buy white carrots"
                    }
                    2 -> {
                        noticesText = "Today at 18:09"
                    }
                    3 -> {
                        noticesText = "Go outside and walk the dog for 30 minutes"
                    }
                    4 -> {
                        noticesText = "Tomorrow at 8:30"
                    }
                    5 -> {
                        noticesText = "Wake up and bench press 20"
                    }
                }
                drawLabel(canvas, notices[i], noticesText)
            }
        }
    }

    /**
     * 绘制提醒功能
     *
     * @param canvas
     */
    private fun drawFansInfo(canvas: Canvas) {
        if (mSkin == null) {
            return
        }

        //TODO 需要更新数据维护逻辑
        val fansInfo: List<RobotSkinLabel?>? = mSkin!!.fansInfo
        var fansText: String = ""

        if (fansInfo != null) {
            for (i in fansInfo.indices) {
                if (i == 0) {
                    fansText = "圆梦是个PM"
                } else if (i == 1) {
                    fansText = "1423.1w"
                } else if (i == 2) {
                    fansText = "粉丝"
                }
                drawLabel(canvas, fansInfo.get(i)!!, fansText)
            }
        }
    }

    fun getCtrlId(x: Int, y: Int): Int {
        if (mSkin?.bluetooth != null && mSkin!!.bluetooth!!.getDispTouchRect() != null) {
            if (mSkin!!.bluetooth!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_BLUETOOTH_ID
            }
        }

        if (mSkin?.volume != null && mSkin!!.volume!!.getDispTouchRect() != null) {
            if (mSkin!!.volume?.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_MEDIA_VOLUME_ID
            }
        }

        if (mSkin?.wifi != null && mSkin?.wifi!!.getDispTouchRect() != null) {
            if (mSkin?.wifi!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_WIFI_ID
            }
        }

        if (mSkin?.step != null && mSkin?.step!!.getDispTouchRect() != null) {
            if (mSkin?.step!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_STEP_ID
            }
        }

        if (mSkin?.airTemp != null && mSkin?.airTemp!!.getDispTouchRect() != null) {
            if (mSkin?.airTemp!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_WEATHER_ID
            }
        }

        if (mSkin?.weather != null && mSkin?.weather!!.getDispTouchRect() != null) {
            if (mSkin?.weather!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_WEATHER_ID
            }
        }

        if (mSkin?.aqiNumber != null && mSkin?.aqiNumber!!.getDispTouchRect() != null) {
            if (mSkin?.aqiNumber!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_WEATHER_ID
            }
        }

        if (mSkin?.aqiText != null && mSkin?.aqiText!!.getDispTouchRect() != null) {
            if (mSkin?.aqiText!!.getDispTouchRect()?.contains(x, y) == true) {
                return RobotClockSkin.CTRL_WEATHER_ID
            }
        }

        return RobotClockSkin.CTRL_NONE_ID
    }

    override fun updateBatteryLevel(batteryLevel: Int) {
        if (mPlatformState!!.batteryLevel != batteryLevel) {
            mPlatformState!!.batteryLevel = batteryLevel
            invalidate()
        }
    }

    override fun updateWifiEnabled(wifiEnabled: Boolean) {
        if (mPlatformState!!.isWifiEnabled() != wifiEnabled) {
            mPlatformState!!.setWifiEnabled(wifiEnabled)
            invalidate()
        }
    }

    override fun updateStepNumber(stepNumber: Int) {
        if (mPlatformState!!.getStepNumber() != stepNumber) {
            mPlatformState!!.setStepNumber(stepNumber)
            invalidate()
        }
    }

    override fun updateMediaVolume(mediaVolume: Int) {
        if (mPlatformState!!.getMediaVolume() != mediaVolume) {
            mPlatformState!!.setMediaVolume(mediaVolume)
            invalidate()
        }
    }

    override fun updateBluetoothEnabled(bluetoothEnabled: Boolean) {
        if (mPlatformState!!.isBluetoothEnabled() != bluetoothEnabled) {
            mPlatformState!!.setBluetoothEnabled(bluetoothEnabled)
            invalidate()
        }
    }

    override fun updateBatteryCharging(batteryCharging: Boolean) {
        if (mPlatformState!!.isBatteryCharging() != batteryCharging) {
            mPlatformState!!.setBatteryCharging(batteryCharging)
            invalidate()
        }
    }

    override fun updateWeather(weatherState: Int, currentTemp: Int, airQuality: Int) {
        if (mPlatformState!!.getWeatherState() != weatherState || mPlatformState!!.getCurrentTemp() != currentTemp || mPlatformState!!.getAirQuality() != airQuality) {
            mPlatformState!!.setWeatherState(weatherState)
            mPlatformState!!.setCurrentTemp(currentTemp)
            mPlatformState!!.setAirQuality(airQuality)

            invalidate()
        }
    }

    override fun updateWeatherDes(weatherState: Int, weatherStateStr: String?, currentTemp: Int) {
        Log.e("letianpai", "========= =======updateViews ============ 8 ===========:")

        //        if (mPlatformState.getWeatherState() != weatherState ||
//                mPlatformState.currentTemp != currentTemp ||
//                (!mPlatformState.weatherStateStr.equals(weatherStateStr))) {
//            Log.e("letianpai","========= =======updateViews ============ 9 ===========:");
//            mPlatformState.weatherState = weatherState;
//            mPlatformState.currentTemp = currentTemp;
//            mPlatformState.weatherStateStr = weatherStateStr;
//            Log.e("letianpai","========= =======updateViews ============ 10 ===========:");
//            invalidate();
//        }
        mPlatformState!!.setWeatherState(weatherState)
        mPlatformState!!.setCurrentTemp(currentTemp)
        mPlatformState!!.setWeatherStateStr(weatherStateStr)
        Log.e("letianpai", "========= =======updateViews ============ 10 ===========:")
        invalidate()
    }

    override fun updateAll(state: RobotPlatformState?) {
        mPlatformState = state
        invalidate()
    }

    val isRefreshPerSecond: Boolean
        get() {
            if (mSkin == null) {
                return false
            }
            return (isRefreshNow && mSkin!!.isRefreshPerSecond)
        }

    fun setRefreshNow(refreshNow: Boolean) {
        isRefreshNow = refreshNow
        if (isRefreshNow) {
            startTimerTask()
        } else {
            stopTimerTask()
        }
    }

    private fun initTimerTask() {
        mTimer = null
        mTimerTask = null
        if (mTimer == null) {
            mTimer = Timer()
        }
        if (mTimerTask == null) {
            mTimerTask = object : TimerTask() {
                override fun run() {
//                    LogUtils.logi("test", "refrash~~~~~");
                    postInvalidate()
                }
            }
        }
    }

    private fun startTimerTask() {
        initTimerTask()
        mTimer!!.schedule(mTimerTask, 0, 50)
    }

    //    private void startTimerTask() {
    //        initTimerTask();
    //        if (isRefreshPerSecond()) {
    //            mTimer.schedule(mTimerTask, 0, 1000);
    //        } else {
    //            Calendar calendar = Calendar.getInstance();
    //            int second = calendar.get(Calendar.SECOND);
    //            mTimer.schedule(mTimerTask, (60 - second) * 1000, 1000 * 60);
    //        }
    //    }
    private fun stopTimerTask() {
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
            mTimerTask = null
        }
    }

    companion object {
        private val TAG: String = RobotClockView::class.java.getSimpleName()

        const val SPINE_CLOCK_HOURS_DEF: Int = 0
        const val SPINE_CLOCK_HOURS_12: Int = 1
        const val SPINE_CLOCK_HOURS_24: Int = 2
        const val TEXT_COLOR_GARY: Int = 7566197
        const val YYYYMMDD_FORMAT: String = "yyyy.MM.dd"
        const val MMDD_FORMAT: String = "MM.dd"
        const val THAI: String = "th"
        const val GERMAN: String = "de"

        private val NO_PERCENT_SIGN: String = "no_percent"

        // 5分19帧   7秒19帧
        /**
         * 高斯模糊
         *
         * @param context
         * @param source
         * @param radius
         * @param scale
         * @return
         */
        private fun rsBlur(
            context: Context?,
            source: Bitmap?,
            radius: Float,
            scale: Float
        ): Bitmap? {
            if (source == null) {
                return null
            }
            val scaleWidth: Int = (source.getWidth() * scale).toInt()
            val scaleHeight: Int = (source.getHeight() * scale).toInt()
            val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(
                source, scaleWidth,
                scaleHeight, false
            )

            val inputBitmap: Bitmap = scaledBitmap

            //TODO 是否更新刷新机制以节约资源
//        LogUtils.logi("RenderScriptActivity", "size:" + inputBitmap.getWidth() + "," + inputBitmap.getHeight());

            //创建RenderScript
            val renderScript: RenderScript = RenderScript.create(context)

            //创建Allocation
            val input: Allocation = Allocation.createFromBitmap(
                renderScript,
                inputBitmap,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT
            )
            val output: Allocation = Allocation.createTyped(renderScript, input.getType())

            //创建ScriptIntrinsic
            val intrinsicBlur: ScriptIntrinsicBlur =
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

            intrinsicBlur.setInput(input)

            intrinsicBlur.setRadius(radius)

            intrinsicBlur.forEach(output)

            output.copyTo(inputBitmap)

            renderScript.destroy()

            return inputBitmap
        }
    }
}

