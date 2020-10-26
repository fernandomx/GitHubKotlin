package com.androidgit.oauth2.model


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.annotations.Expose
import java.io.*
import kotlin.jvm.Throws


class GitHubOwner:  Serializable  {
    lateinit var imageByteArray: ByteArray

    @Expose
    var login: String? = null

    @Expose
    var id: Integer? = null

    @Expose
    var avatar_url: String? = null

    var picture: Bitmap? = null



    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(login)
        out.writeObject(id)
        out.writeObject(avatar_url)
        val byteStream = ByteArrayOutputStream()
        picture!!.compress(Bitmap.CompressFormat.PNG, 0, byteStream)
        val bitmapBytes = byteStream.toByteArray()
        out.write(bitmapBytes, 0, bitmapBytes.size)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        login = `in`.readObject() as String
        id = `in`.readObject() as Integer
        avatar_url = `in`.readObject() as String
        val byteStream = ByteArrayOutputStream()
        var b: Int
        while (`in`.read().also { b = it } != -1) byteStream.write(b)
        val bitmapBytes = byteStream.toByteArray()
        picture = BitmapFactory.decodeByteArray(bitmapBytes, 0,
                bitmapBytes.size)
    }




}