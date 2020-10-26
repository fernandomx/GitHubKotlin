package com.androidgit.oauth2.model.helper

class ConstantsGit {


    object DATABASE {
        const val DB_NAME = "git.db"
        const val DB_VERSION = 1

        //github
        const val TABLE_NAME = "github"
        const val DROP_QUERY = "DROP TABLE IF EXIST $TABLE_NAME"
        const val GET_GIT_QUERY = "SELECT * FROM $TABLE_NAME"
        const val ID = "id"
        const val NAME = "name"
        const val LOGIN = "login"
        const val STARSCOUNT = "starscount"
        const val FORKSCOUNT = "forkscount"
        const val PHOTO_URL = "photo_url"
        const val PHOTO = "photo"
        const val CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + ID + " INTEGER PRIMARY KEY ," +
                NAME + " TEXT , " +
                LOGIN + " TEXT , " +
                STARSCOUNT + " INTEGER , " +
                FORKSCOUNT + " INTEGER , " +
                PHOTO_URL + " TEXT ," +
                PHOTO + " blob )"

    }

    object REFERENCE {
        const val GITHUB = Config.PACKAGE_NAME + "GitHub"
    }

    object Config {
        const val PACKAGE_NAME = "com.androidgit.oauth2.model.helper."
    }
}