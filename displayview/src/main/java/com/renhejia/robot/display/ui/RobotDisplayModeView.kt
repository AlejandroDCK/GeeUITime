package com.renhejia.robot.display.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.renhejia.robot.display.R
import com.renhejia.robot.display.SpineSkinResPool
import java.util.Timer
import java.util.TimerTask

class RobotDisplayModeView : RelativeLayout {
    private var displayView: RobotDisplayView? = null
    private val mContext: Context? = null
    private var mSkinList: List<String>? = emptyList()
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    private var currentIndex: Int = 0


    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inflate(mContext, R.layout.view_display, this)
        initData()
        initView()
        fillDataAndUpdate()
    }

    private fun fillDataAndUpdate() {
        loadDisplayView()
        updateDisplayViews()
    }


    private fun initData() {
        mSkinList = mContext?.let { SpineSkinResPool.getDisplayList(it) }
    }

    private fun initView() {
        displayView = findViewById(R.id.rdv_display)
    }


    private fun loadDisplayView() {
        displayView!!.loadSkin(SKIN_PATH)
    }

    private fun loadDisplayView(position: Int) {
        displayView!!.loadSkin(mSkinList!!.get(position))
    }

    fun updateDisplayViews() {
        mTimer = Timer()
        mTimerTask = object : TimerTask() {
            override fun run() {
                currentIndex += 1
                if (currentIndex >= mSkinList!!.size) {
                    currentIndex = 0
                }
                loadDisplayView(currentIndex)
            }
        }
        //        mTimer.schedule(mTimerTask, 5000, 5000);
        mTimer!!.schedule(mTimerTask, 5000, 5000)
    }

    companion object {
        private val SKIN_PATH: String = "display/time"
    }
}
