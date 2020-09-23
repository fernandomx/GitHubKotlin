package com.androiddesdecero.oauth2.model.helper

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
class Constants {
    object HTTP {
        //const val BASE_URL = "http://services.hanselandpetal.com/"
    }

    object DATABASE {
        const val DB_NAME = "missions"
        const val DB_VERSION = 1
        const val TABLE_NAME = "mission"
        const val DROP_QUERY = "DROP TABLE IF EXIST $TABLE_NAME"
        const val GET_MISSIONS_QUERY = "SELECT * FROM $TABLE_NAME"
        const val ID = "id"
        const val NAME = "name"
        const val STATUS = "status"
        const val CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + ID + " INTEGER PRIMARY KEY not null," +
                NAME + " TEXT not null," +
                STATUS + " TEXT not null)"
    }

    object REFERENCE {
        const val MISSIONS = Config.PACKAGE_NAME + "Missions"
    }

    object Config {
        const val PACKAGE_NAME = "com.androiddesdecero.oauth2.model.helper."
    }
}