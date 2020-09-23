package com.androiddesdecero.oauth2.model.database

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.androiddesdecero.oauth2.model.Missions
import com.androiddesdecero.oauth2.model.callback.MissionsFetchListener
import com.androiddesdecero.oauth2.model.helper.Constants
import com.androiddesdecero.oauth2.model.helper.Utils.getPictureByteOfArray
import java.util.ArrayList

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */

class MissionsDatabase(context: Context?) : SQLiteOpenHelper(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY)
        } catch (ex: SQLException) {
            Log.d(TAG, ex.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Constants.DATABASE.DROP_QUERY)
        onCreate(db)
    }

    fun addMissions(missions: Missions) {
        Log.d(TAG, "Values Got " + missions.name)
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constants.DATABASE.ID, missions.id)
        values.put(Constants.DATABASE.NAME, missions.name)
        values.put(Constants.DATABASE.STATUS, missions.status)
        //values.put(Constants.DATABASE.PHOTO, getPictureByteOfArray(flower.picture!!))
        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values)

        } catch (e: Exception) {
        }
        db.close()
    }

    fun updMissions(missions: Missions) {
        Log.d(TAG, "Values update " + missions.name)


        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constants.DATABASE.NAME, missions.name)
        values.put(Constants.DATABASE.STATUS, missions.status)

        try {
            //db.insert(Constants.DATABASE.TABLE_NAME, null, values)
            db.update(Constants.DATABASE.TABLE_NAME,values,null,null)
        } catch (e: Exception) {
        }
        db.close()


    }





    fun fetchMissions(listener: MissionsFetchListener) {
        val fetcher = MissionsFetcher(listener, this.writableDatabase)
        fetcher.start()
    }

    inner class MissionsFetcher(private val mListener: MissionsFetchListener, private val mDb: SQLiteDatabase) : Thread() {
        override fun run() {
            val cursor = mDb.rawQuery(Constants.DATABASE.GET_MISSIONS_QUERY, null)
            val missionsList: MutableList<Missions> = ArrayList()
            if (cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val mission = Missions()
                        mission.isFromDatabase = true
                        mission.id = cursor.getString(cursor.getColumnIndex(Constants.DATABASE.ID)).toInt()
                        mission.name = cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME))
                        mission.status = cursor.getString(cursor.getColumnIndex(Constants.DATABASE.STATUS))
                        //flower.picture = getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Constants.DATABASE.PHOTO)))

                        missionsList.add(mission)
                        publishMission(mission)
                    } while (cursor.moveToNext())
                }
            }
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                mListener.onDeliverAllMissions(missionsList)
                mListener.onHideDialog()
            }
        }

        fun publishMission(missions: Missions?) {
            val handler = Handler(Looper.getMainLooper())
            handler.post { mListener.onDeliverMissions(missions) }
        }

    }

    companion object {
        private val TAG = MissionsDatabase::class.java.simpleName
    }
}