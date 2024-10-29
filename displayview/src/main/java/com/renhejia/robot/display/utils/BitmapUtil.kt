package com.renhejia.robot.display.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Bitmap 工具类
 *
 * @author liujunbin
 */
class BitmapUtil {
    fun getBitmapByte(bitmap: Bitmap): ByteArray {
        val out: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        try {
            out.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out.toByteArray()
    }


    companion object {
        fun getBitmapFromByte(temp: ByteArray?): Bitmap? {
            if (temp != null) {
                val bitmap: Bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.size)
                return bitmap
            } else {
                return null
            }
        }


        fun drawableToBitmap(drawable: Drawable): Bitmap {
            val width: Int = drawable.getIntrinsicWidth()

            val height: Int = drawable.getIntrinsicHeight()

            val bitmap: Bitmap = Bitmap.createBitmap(
                width, height,

                if (drawable.getOpacity() != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565
            )

            val canvas: Canvas = Canvas(bitmap)

            drawable.setBounds(0, 0, width, height)

            drawable.draw(canvas)

            return bitmap
        }

        /**
         * 照片转byte二进制
         *
         * @param imagepath 需要转byte的照片路径
         * @return 已经转成的byte
         * @throws Exception
         */
        @Throws(Exception::class)
        fun convertImageToByte(imagepath: String?): ByteArray {
            val fs: FileInputStream = FileInputStream(imagepath)
            val outStream: ByteArrayOutputStream = ByteArrayOutputStream()
            val buffer: ByteArray = ByteArray(1024)
            var len: Int = 0
            while (-1 != (fs.read(buffer).also { len = it })) {
                outStream.write(buffer, 0, len)
            }
            outStream.close()
            fs.close()
            return outStream.toByteArray()
        }

        /**
         * 合并图片
         *
         * @param firstBitmap
         * @param secondBitmap
         * @return
         */
        fun mergeBitmap(firstBitmap: Bitmap, secondBitmap: Bitmap): Bitmap {
//        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),firstBitmap.getConfig());
            val bitmap: Bitmap = Bitmap.createBitmap(
                secondBitmap.getWidth(),
                secondBitmap.getHeight(),
                secondBitmap.getConfig()
            )
            val canvas: Canvas = Canvas(bitmap)
            canvas.drawBitmap(firstBitmap, Matrix(), null)
            canvas.drawBitmap(secondBitmap, 0f, 0f, null)
            return bitmap
        }

        /**
         * 合并图片
         *
         * @param firstBitmap
         * @param secondBitmap
         * @return
         */
        fun mergeBitmap(firstBitmap: Bitmap, secondBitmap: Bitmap, left: Int, top: Int): Bitmap {
            val bitmap: Bitmap = Bitmap.createBitmap(
                firstBitmap.getWidth(),
                firstBitmap.getHeight(),
                firstBitmap.getConfig()
            )
            val canvas: Canvas = Canvas(bitmap)
            canvas.drawBitmap(firstBitmap, Matrix(), null)
            canvas.drawBitmap(secondBitmap, left.toFloat(), top.toFloat(), null)
            return bitmap
        }

        /**
         * 将bitmap 转为 File
         *
         * @param bitmap
         * @return
         */
        fun saveBitmapToFile(bitmap: Bitmap, fileName: String): File {
            val baos: ByteArrayOutputStream = ByteArrayOutputStream()
            //        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos)
            //        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            val file: File = File(fileName)
            try {
                file.createNewFile()
                val fos: FileOutputStream = FileOutputStream(file)
                val `is`: InputStream = ByteArrayInputStream(baos.toByteArray())
                var x: Int = 0
                val b: ByteArray = ByteArray(1024 * 100)
                while ((`is`.read(b).also { x = it }) != -1) {
                    fos.write(b, 0, x)
                }
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }

        /**
         * 加载asset文件夹中的bitmap
         * @param context
         * @param filename
         * @return
         */
        fun loadBitmapFromAssetFilename(context: Context, filename: String): Bitmap? {
            var inputStream: InputStream? = null
            var bitmap: Bitmap? = null

            try {
                inputStream = context.getResources().getAssets().open(filename)
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }


            return bitmap
        }

        /**
         * 加载asset文件夹中的bitmap
         * @param filename
         * @return
         */
        fun convertImageToBitmap(filename: String?): Bitmap? {
            var bitmap: Bitmap? = null

            try {
                val fis: FileInputStream = FileInputStream(filename)
                bitmap = BitmapFactory.decodeStream(fis)
            } catch (e: Exception) {
                e.printStackTrace()
            }


            return bitmap
        }
    }
}
