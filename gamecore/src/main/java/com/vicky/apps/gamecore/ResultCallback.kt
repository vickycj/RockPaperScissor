package com.vicky.apps.gamecore

interface ResultCallback {

    fun onProgress(progress:Long)
    fun onResult(result: Result)
}