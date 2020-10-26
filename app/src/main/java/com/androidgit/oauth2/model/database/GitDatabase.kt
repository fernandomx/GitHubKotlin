package com.androidgit.oauth2.model.database

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.androidgit.oauth2.model.GitHub
import com.androidgit.oauth2.model.callback.GitFetchListener
import com.androidgit.oauth2.model.helper.ConstantsGit
import com.androidgit.oauth2.model.helper.Utils.getBitmapFromByte
import com.androidgit.oauth2.model.helper.Utils.getPictureByteOfArray
import java.util.ArrayList

class GitDatabase(context: Context?) : SQLiteOpenHelper(context, ConstantsGit.DATABASE.DB_NAME, null, ConstantsGit.DATABASE.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(ConstantsGit.DATABASE.CREATE_TABLE_QUERY)
        } catch (ex: SQLException) {
            Log.d(TAG, ex.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(ConstantsGit.DATABASE.DROP_QUERY)
        onCreate(db)
    }

    fun addGitHubs(githubs: GitHub) {
        Log.d(TAG, "Values " + githubs.name)

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ConstantsGit.DATABASE.ID, githubs.id)
        values.put(ConstantsGit.DATABASE.NAME, githubs.name)
        values.put(ConstantsGit.DATABASE.LOGIN, githubs.owner?.login)
        values.put(ConstantsGit.DATABASE.STARSCOUNT, githubs.stargazers_count)
        values.put(ConstantsGit.DATABASE.FORKSCOUNT, githubs.forks_count)

        values.put(ConstantsGit.DATABASE.PHOTO_URL, githubs.owner?.avatar_url)
        values.put(ConstantsGit.DATABASE.PHOTO, getPictureByteOfArray(githubs.owner?.picture!!))

        try {
            db.insert(ConstantsGit.DATABASE.TABLE_NAME, null, values)

        } catch (e: Exception) {
        }
        db.close()
    }



    fun fetchGitHubs(listener: GitFetchListener) {
        val fetcher = GitFetcher(listener, this.writableDatabase)
        fetcher.start()
    }

    inner class GitFetcher(private val mListener: GitFetchListener, private val mDb: SQLiteDatabase) : Thread() {
        override fun run() {
            val cursor = mDb.rawQuery(ConstantsGit.DATABASE.GET_GIT_QUERY, null)
            val gitList: MutableList<GitHub> = ArrayList()
            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val git = GitHub()
                        git.isFromDatabase = true
                        git.id = cursor.getString(cursor.getColumnIndex(ConstantsGit.DATABASE.ID)).toInt()
                        git.name = cursor.getString(cursor.getColumnIndex(ConstantsGit.DATABASE.NAME))
                        git.owner?.login = cursor.getString(cursor.getColumnIndex(ConstantsGit.DATABASE.LOGIN))

                        git.owner?.picture = getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(ConstantsGit.DATABASE.PHOTO)))
                        git.stargazers_count = cursor.getString(cursor.getColumnIndex(ConstantsGit.DATABASE.STARSCOUNT)).toInt()
                        git.forks_count = cursor.getString(cursor.getColumnIndex(ConstantsGit.DATABASE.FORKSCOUNT)).toInt()


                        gitList.add(git)
                        publishGit(git)
                    } while (cursor.moveToNext())
                }
            }
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                mListener.onDeliverAllGit(gitList)
                mListener.onHideDialog()
            }
        }

        fun publishGit(githubs: GitHub?) {
            val handler = Handler(Looper.getMainLooper())
            handler.post { mListener.onDeliverGit(githubs) }
        }

    }

    companion object {
        private val TAG = GitDatabase::class.java.simpleName
    }
}