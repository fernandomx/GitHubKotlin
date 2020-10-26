package com.androidgit.oauth2.model.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import java.io.ByteArrayOutputStream

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */

object Utils {
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var available = false
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            available = networkInfo != null && networkInfo.isConnectedOrConnecting
        }
        return available
    }

    @JvmStatic
    fun getPictureByteOfArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    @JvmStatic
    fun getBitmapFromByte(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }
}