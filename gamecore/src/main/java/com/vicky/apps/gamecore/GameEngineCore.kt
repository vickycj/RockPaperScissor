package com.vicky.apps.gamecore

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception
import java.util.concurrent.TimeUnit

class GameEngineCore(private val counterTime: Long = GameConstants.COUNTER_3_SECONDS,
                     private val gameObjectCount:Int = GameConstants.THREE_OBJECT_GAME) : GamePlay {

    private lateinit var gameType:GameType

    private var timer:Observable<Long>? = null

    private lateinit var result: Observable<Result>

    private lateinit var playerA: Player

    private lateinit var playerB: Player

    private lateinit var gameObjects:List<GameObject>

    override fun initializeGame(gameType: GameType) {
        this.gameType = gameType
        initializePlayer()
        gameObjects = initializeGameObjects()
    }

    override fun timerSubscription(): Observable<Long>? = timer
    override fun resultSubscription(): Observable<Result> = result
    override fun getListOfGameObjects(): List<GameObject> = gameObjects
    override fun assignPlayerSelection(gameObject: GameObject) {
        if(gameType == GameType.PLAYER_VS_COMPUTER)
            playerB.gameObject = gameObject
        else
            throw Exception()
    }

    override fun startResult() {
        initTimer()
    }

    private fun emitResults(){
        result = Observable.just(generateResult(playerA,playerB))
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun initTimer() {
       timer =  Observable.interval(counterTime, TimeUnit.SECONDS)
           .observeOn(AndroidSchedulers.mainThread())
           .doOnComplete {
               assignValues()
               emitResults()
           }
    }

    private fun assignValues() {
        if(gameType == GameType.PLAYER_VS_COMPUTER){
            playerA.gameObject = getRandomGameObject()
        }else if(gameType == GameType.COMPUTER_VS_COMPUTER){
            playerA.gameObject = getRandomGameObject()
            playerB.gameObject = getRandomGameObject()
        }
    }

    private fun generateResult(playerA:Player, playerB: Player):Result{
        return when(playerA.gameObject.attacks.contains(playerB.gameObject)){
            true -> Result(playerA, playerA.gameObject)
            false -> Result(playerB,playerB.gameObject)
        }
    }

    private fun initializeGameObjects(): List<GameObject>{
        val rock = GameObject(GameConstants.OBJECT_NAME_ROCK)
        val paper = GameObject(GameConstants.OBJECT_NAME_PAPER)
        val scissor = GameObject(GameConstants.OBJECT_NAME_SCISSOR)

        rock.attacks = arrayListOf(scissor)
        rock.defends = arrayListOf(paper)

        paper.attacks = arrayListOf(rock)
        paper.defends = arrayListOf(scissor)

        scissor.attacks = arrayListOf(paper)
        scissor.defends = arrayListOf(rock)

        return arrayListOf(rock,paper,scissor)
    }

    private fun initializePlayer(){
        if (gameType == GameType.PLAYER_VS_COMPUTER){
            playerA = Player(GameConstants.PLAYER_TYPE_COMPUTER)
            playerB = Player(GameConstants.PLAYER_TYPE_HUMAN)
        }else if(gameType == GameType.COMPUTER_VS_COMPUTER){
            playerA = Player(GameConstants.PLAYER_TYPE_COMPUTER)
            playerB = Player(GameConstants.PLAYER_TYPE_COMPUTER)
        }
    }

    private fun getRandomGameObject():GameObject{
        val totalCount = gameObjectCount - 1
        return gameObjects[(0..totalCount).shuffled().first()]
    }

}