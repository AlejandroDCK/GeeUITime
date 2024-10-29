package com.renhejia.robot.display.personal

import android.content.Context
import android.content.res.AssetManager
import android.util.Xml
import com.renhejia.robot.display.parser.Middle
import com.renhejia.robot.display.utils.Consts
import org.xmlpull.v1.XmlPullParser
import java.io.FileInputStream
import java.io.InputStream

/**
 * @author liujunbin
 */
object XMLParsingUtils {
    fun getSkinCustomInputStreamFromAsset(context: Context, filename: String): InputStream? {
        val mAssetManager: AssetManager = context.getResources().getAssets()
        var inputStream: InputStream? = null

        try {
            inputStream = mAssetManager.open(filename)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return inputStream
    }

    fun getSkinCustomInputStreamFromSD(context: Context?, filename: String?): InputStream? {
        var fileInputStream: InputStream? = null

        try {
            fileInputStream = FileInputStream(filename)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fileInputStream
    }

    fun getMiddleNameList(inputStream: InputStream): List<Middle?>? {
        val parser: XmlPullParser = Xml.newPullParser()
        try {
            parser.setInput(inputStream, "UTF-8")
            var eventType: Int = parser.getEventType()

            var currentMiddle: Middle? = null
            var middleList: MutableList<Middle?>? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> middleList = ArrayList()
                    XmlPullParser.START_TAG -> {
                        val name: String = parser.getName()
                        if (name == Consts.MIDDLE) {
                            currentMiddle = Middle()
                        } else if (currentMiddle != null) {
                            if (name == Consts.NAME) {
                                val name3: String = parser.nextText()
                                currentMiddle.name = name3
                            } else if (name == Consts.ID) {
                                val id: String = parser.nextText()
                                currentMiddle.id = id.toInt()
                            }
                        }
                    }

                    XmlPullParser.END_TAG -> if (parser.getName()
                            .equals(Consts.MIDDLE, ignoreCase = true) && currentMiddle != null
                    ) {
                        middleList!!.add(currentMiddle)
                        currentMiddle = null
                    }
                }
                eventType = parser.next()
            }
            inputStream.close()

            return middleList
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取 Middle 名字描述列表
     *
     * @param context
     * @param filename
     * @return
     */
    fun getMiddleDesNameList(
        context: Context,
        filename: String,
        isFromSdCard: Boolean
    ): List<Middle?>? {
        var middleStream: InputStream? = null
        if (isFromSdCard) {
            middleStream = getSkinCustomInputStreamFromSD(context, filename)
        } else {
            middleStream = getSkinCustomInputStreamFromAsset(context, filename)
        }

        if (middleStream == null) {
            return null
        }
        return getMiddleNameList(middleStream)
    }
}
