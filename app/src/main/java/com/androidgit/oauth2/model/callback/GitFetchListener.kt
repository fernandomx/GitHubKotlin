package com.androidgit.oauth2.model.callback

import com.androidgit.oauth2.model.GitHub

interface GitFetchListener {


    fun onDeliverAllGit(githubs: List<GitHub?>?)
    fun onDeliverGit(githubs: GitHub?)
    fun onHideDialog()

}