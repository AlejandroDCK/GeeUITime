package com.renhejia.robot.display.utils

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * 表盘换肤工具类
 * @author liujunbin
 */
object SpineSkinUtils {
    private val FOREGROUND: String = "foreground.png"
    private val FOREGROUND_ZH: String = "foreground_zh.png"

    /**
     * 图片合并
     *
     * @param foregroundBitmap
     * @param backgroundBitmap
     * @return
     */
    fun mergeBitmap(backgroundBitmap: Bitmap, foregroundBitmap: Bitmap): Bitmap {
        return BitmapUtil.Companion.mergeBitmap(backgroundBitmap, foregroundBitmap)
    }

    /**
     * 转化Bitmap to File
     * @param bitmap
     * @param fileName
     * @return
     */
    fun saveBitmapToFile(bitmap: Bitmap, fileName: String): File {
        return BitmapUtil.Companion.saveBitmapToFile(bitmap, fileName)
    }


    /**
     * 创建默认缩略图文件
     *
     * @param context
     * @param skinName
     * @return
     */
    fun createThumbFile(context: Context, skinName: String): File {
        val foreground: String = skinName + "/" + FOREGROUND
        return createThumb(context, skinName, foreground, false)
    }

    /**
     * 创建缩略图
     *
     * @param context
     * @param skinName
     * @return
     */
    fun createThumb(
        context: Context,
        skinName: String,
        foreground: String,
        isChinese: Boolean
    ): File {
        val background: String = getFileNameOfChangeSkinBackground(context, skinName)
        val foregroundBitmap: Bitmap? =
            BitmapUtil.Companion.loadBitmapFromAssetFilename(context, foreground)

        var backgroundBitmap: Bitmap? = BitmapFactory.decodeFile(background)

        backgroundBitmap = Bitmap.createScaledBitmap(
            backgroundBitmap!!,
            320,
            320,  //                    LauncherConfigManager.getInstance(context).getWatchWidth(),
            //                    LauncherConfigManager.getInstance(context).getWatchHeight(),
            true
        )

        val newBackgroundBitmap: Bitmap = mergeBitmap(
            backgroundBitmap,
            foregroundBitmap!!
        )
        var fileName: String = ""
        if (isChinese) {
            fileName = getFileNameOfChangeSkinChineseThumb(context, skinName)
        } else {
            fileName = getFileNameOfChangeSkinThumb(context, skinName)
        }

        return saveBitmapToFile(newBackgroundBitmap, fileName)
    }

    /**
     * 创建中文缩略图文件
     *
     * @param context
     * @param skinName
     * @return
     */
    fun createChineseThumbFile(context: Context, skinName: String): File {
        val foreground: String = skinName + "/" + FOREGROUND_ZH
        return createThumb(context, skinName, foreground, true)
    }

    /**
     * 备份图片到目标路径
     * @param context
     * @param oldFile
     * @param spineSkinName
     */
    fun copyBackground(context: Context, oldFile: String, spineSkinName: String?) {
        val newFile: String = getFileNameOfChangeSkinBackground(context, spineSkinName)
        FileUtil.copyFile(oldFile, newFile)
    }

    /**
     *
     * @param context
     * @param oldFile
     * @param spineSkinName
     */
    fun createBackground(context: Context, oldFile: String?, spineSkinName: String?) {
        val newFile: String = getFileNameOfChangeSkinBackground(context, spineSkinName)
        val file: File = File(newFile)
        val parentDir: File? = file.getParentFile()
        if (!parentDir!!.exists()) {
            parentDir.mkdirs()
        }
        var backgroundBitmap: Bitmap? = BitmapFactory.decodeFile(oldFile)
        backgroundBitmap = Bitmap.createScaledBitmap(
            backgroundBitmap!!,
            320,
            320,  //                LauncherConfigManager.getInstance(context).getWatchWidth(),
            //                LauncherConfigManager.getInstance(context).getWatchHeight(),
            true
        )
        saveBitmapToFile(backgroundBitmap, newFile)
    }

    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    fun getFileNameOfChangeSkinBackground(context: Context, spineSkinName: String?): String {
        val spinePath: String = "/data/data/" + context.getPackageName() + "/spine_skin/"
        val backgroundImage: String = spinePath + spineSkinName + "_background.png"

        return backgroundImage
    }

    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    fun getFileNameOfChangeSkinThumb(context: Context, spineSkinName: String): String {
        val spinePath: String = "/data/data/" + context.getPackageName() + "/spine_skin/"
        val backgroundImage: String = spinePath + spineSkinName + "_thumb.png"
        return backgroundImage
    }

    /**
     * 获取图片存储路径
     * @param context
     * @param spineSkinName
     * @return
     */
    fun getFileNameOfChangeSkinChineseThumb(context: Context, spineSkinName: String): String {
        val spinePath: String = "/data/data/" + context.getPackageName() + "/spine_skin/"
        val backgroundImage: String = spinePath + spineSkinName + "_thumb_zh.png"
        return backgroundImage
    }

    /**
     * 是否换肤缩略图存在
     * @param context
     * @param skinName
     * @return
     */
    fun isChangeSkinThumbExit(context: Context, skinName: String): Boolean {
        val filePath: String = getFileNameOfChangeSkinThumb(context, skinName)
        val file: File = File(filePath)
        if (file.exists()) {
            return true
        } else {
            return false
        }
    }

    /**
     * 是否换肤缩略图存在
     * @param context
     * @param skinName
     * @return
     */
    fun isChangeSkinBackgroundExit(context: Context, skinName: String?): Boolean {
        val filePath: String = getFileNameOfChangeSkinBackground(context, skinName)
        val file: File = File(filePath)
        if (file.exists()) {
            return true
        } else {
            return false
        }
    }

    /**
     * 获取文件名
     *
     * @param downloadUrl
     * @return
     */
    fun getFileName(downloadUrl: String?): String? {
        return FileUtil.getFileName(downloadUrl)
    }

    fun getDefaultSkin(context: Context): String? {
        val gson: Gson = Gson()
        val mSkinList: Array<String>
        val JSON_FILE_NAME: String = "skin_list.json"
        val jsonString: String = getJson(JSON_FILE_NAME, context)
        mSkinList = gson.fromJson(jsonString, Array<String>::class.java)
        if (mSkinList.size > 0) {
            return mSkinList.get(0)
        }

        return null
    }

    fun getSkinList(context: Context): Array<String> {
        val gson: Gson = Gson()
        val mSkinList: Array<String>
        val JSON_FILE_NAME: String = "skin_list.json"
        val jsonString: String = getJson(JSON_FILE_NAME, context)
        mSkinList = gson.fromJson(jsonString, Array<String>::class.java)

        return mSkinList
    }


    private fun getJson(fileName: String, context: Context): String {
        val stringBuilder: StringBuilder = StringBuilder()
        val fullname: String = fileName

        try {
            val assetManager: AssetManager = context.getAssets()
            val bf: BufferedReader = BufferedReader(
                InputStreamReader(
                    assetManager.open(fullname)
                )
            )
            var line: String?
            while ((bf.readLine().also { line = it }) != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}


