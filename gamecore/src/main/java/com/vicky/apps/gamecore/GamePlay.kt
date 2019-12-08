package com.vicky.apps.gamecore

import io.reactivex.Observable

interface GamePlay {


    fun initializeGame(gameType: GameType)
    fun timerSubscription():Observable<Long>?
    fun resultSubscription():Observable<Result>
    fun getListOfGameObjects():List<GameObject>
    fun assignPlayerSelection(gameObject: GameObject)
    fun startResult()

}