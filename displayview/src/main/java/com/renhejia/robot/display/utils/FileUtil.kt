package com.renhejia.robot.display.utils

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtil {
    val rootPath: String
        get() {
            var sdDir: String? = null
            val sdCardExist: Boolean =
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED // 判断sd卡是否存在
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() // 获取跟目录
            }
            if (sdDir != null) {
                return sdDir
            } else {
                return ""
            }
        }

    val surplusSpace: Long
        get() {
            return getAvailableSpace(
                Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
            )
        }

    fun getAvailableSpace(path: String?): Long {
        val statFs: StatFs = StatFs(path)
        //sd卡可用分区数
        val avCounts: Long = statFs.getAvailableBlocksLong()
        //一个分区数的大小
        val blockSize: Long = statFs.getBlockSizeLong()
        //sd卡可用空间
        val spaceLeft: Long = avCounts * blockSize
        return spaceLeft
    }

    fun delete(path: String?): Boolean {
        if (TextUtils.isEmpty(path)) {
            return true
        }
        val file: File = File(path)
        if (!file.exists()) {
            return true
        }
        return file.delete()
    }

    fun copyFile(oldPath: String, newPath: String): Boolean {
        var inStream: InputStream? = null
        var fs: FileOutputStream? = null
        try {
            var byteread: Int = 0
            val oldfile: File = File(oldPath)
            if (!oldfile.exists()) {
                return false
            }
            val newFile: File = File(newPath)
            val parentDir: File? = newFile.getParentFile()
            if (!parentDir!!.exists()) {
                parentDir.mkdirs()
            }
            if (newFile.exists()) {
                newFile.delete()
            }
            newFile.createNewFile()
            inStream = FileInputStream(oldPath) //读入原文件
            fs = FileOutputStream(newPath)
            val buffer: ByteArray = ByteArray(1444)
            while ((inStream.read(buffer).also { byteread = it }) != -1) {
                fs.write(buffer, 0, byteread)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            delete(newPath)
        } finally {
            closeSilence(inStream)
            closeSilence(fs)
        }
        return false
    }


    fun closeSilence(closeable: Closeable?) {
        if (null != closeable) {
            try {
                closeable.close()
            } catch (e: IOException) {
//				e.printStackTrace();
            }
        }
    }

    fun rename(srcFilePath: String, targetFilePath: String): Boolean {
        val srcFile: File = File(srcFilePath)
        val targetFile: File = File(targetFilePath)
        if (targetFile.exists()) {
            targetFile.delete()
        }
        if (srcFile.exists()) {
            return srcFile.renameTo(targetFile)
        }
        return false
    }

    fun getFileName(downloadUrl: String?): String? {
        if (downloadUrl == null) {
            return null
        }
        if (downloadUrl.contains("/")) {
            val apkName: Array<String> =
                downloadUrl.split("\\/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (apkName.size > 1) {
                return apkName.get(apkName.size - 1)
            }
        }
        return null
    }

    /**
     * 判断assets文件夹下的文件是否存在
     *
     * @return false 不存在    true 存在
     */
    fun isFileExists(context: Context, filename: String, skinName: String): Boolean {
        val assetManager: AssetManager = context.getAssets()
        try {
            val names: Array<String>? = assetManager.list(skinName)
            for (i in names!!.indices) {
                if (names.get(i) == filename.trim { it <= ' ' }) {
                    println(filename + "存在")
                    return true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println(filename + "不存在")
            return false
        }
        println(filename + "不存在")
        return false
    }
}