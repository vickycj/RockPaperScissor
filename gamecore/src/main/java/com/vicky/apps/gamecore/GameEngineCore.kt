package com.vicky.apps.gamecore

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class GameEngineCore(private val counterTime: Long = GameConstants.COUNTER_3_SECONDS,
                     private val gameObjectCount:Int = GameConstants.THREE_OBJECT_GAME) : GamePlay {

    private lateinit var gameType:GameType

    private lateinit var resultCallback: ResultCallback

    private lateinit var playerA: Player

    private lateinit var playerB: Player

    private lateinit var gameObjects:List<GameObject>

    private val compositeDisposable = CompositeDisposable()


    override fun initializeGame(gameType: GameType, resultCallback: ResultCallback) {
        this.gameType = gameType
        this.resultCallback = resultCallback
        initializePlayer()
        gameObjects = initializeGameObjects()
    }

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
       resultCallback.onResult(generateResult(playerA,playerB))
       compositeDisposable.dispose()
    }

    private fun initTimer() {
        val disposeTimer =  Observable.interval(0,1, TimeUnit.SECONDS)
           .take(counterTime+1)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe {
               resultCallback.onProgress(counterTime - it)
               if(it == counterTime){
                   assignValues()
                   emitResults()
               }
           }

        compositeDisposable.add(disposeTimer)

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
        if (playerA.gameObject.attacks.contains(playerB.gameObject)) {
            return Result(GameState.PLAYER_A_WON, playerA.gameObject, playerB.gameObject)
        } else if (playerA.gameObject.defends.contains(playerB.gameObject)) {
            return Result(GameState.PLAYER_B_WON, playerA.gameObject, playerB.gameObject)
        } else {
            return Result(GameState.DRAW, playerA.gameObject, playerB.gameObject)
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