package com.vicky.apps.gamecore

class GameEngineCore(private val counterTime: Int = GameConstants.COUNTER_3_SECONDS) : GamePlay {

    private lateinit var gameType:GameType

    override fun initializeGame(gameType: GameType) {
        this.gameType = gameType
    }


}