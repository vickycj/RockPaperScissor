package com.vicky.apps.gamecore


interface GamePlay {


    fun initializeGame(gameType: GameType, resultCallback: ResultCallback)
    fun getListOfGameObjects():List<GameObject>
    fun assignPlayerSelection(gameObject: GameObject)
    fun startResult()

}