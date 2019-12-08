package com.vicky.apps.rockpaperscissor.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vicky.apps.gamecore.*
import com.vicky.apps.rockpaperscissor.ui.model.GameItems
import com.vicky.apps.rockpaperscissor.ui.model.ResultUI


class GameViewModel(private val gamePlay: GamePlay) : ViewModel() {

    private var gameObjects: List<GameObject> = arrayListOf()

    private var playerSelected: Boolean = false

    var gameItemsPlayerA: List<GameItems> = arrayListOf()
    var gameItemsPlayerB: List<GameItems> = arrayListOf()

    val progressLiveData: MutableLiveData<Long> = MutableLiveData()

    val resultLiveData: MutableLiveData<ResultUI> = MutableLiveData()

    fun initializeGame(gameType: GameType) {
        gamePlay.initializeGame(gameType, object : ResultCallback {
            override fun onProgress(progress: Long) {
                onProgressLoader(progress)
            }

            override fun onResult(result: Result) {
                onResultFinal(result)
            }

        })

        gameObjects = gamePlay.getListOfGameObjects()

        gameItemsPlayerA = populateGameObjects()
        gameItemsPlayerB = populateGameObjects()
    }

    fun getPlayerSelected() = playerSelected

    private fun populateGameObjects(): List<GameItems> {
        val gameItems: MutableList<GameItems> = arrayListOf()
        gameObjects.forEach {
            gameItems.add(GameItems(it.item))
        }
        return gameItems
    }

    fun playerSelection(gameItems: GameItems) {

        val gameObject = gameObjects.find {
            it.item == gameItems.item
        }

        gameObject?.let {
            gamePlay.assignPlayerSelection(gameObject)
        }

        gameItemsPlayerB.map {
            it.selected = gameItems.item == it.item
        }

        playerSelected = true
    }

    fun startGame(){
        gamePlay.startResult()
    }

    fun resetGame(){

        playerSelected = false

        resetList()
    }

    fun resetList(){
        resetPlayerA()

        gameItemsPlayerB.map {
            it.selected = false
        }
    }

    fun resetPlayerA(){
        gameItemsPlayerA.map {
            it.selected = false
        }
    }

    private fun onProgressLoader(onProgress: Long) {
        progressLiveData.value = onProgress
    }

    private fun onResultFinal(result: Result) {
       gameItemsPlayerA.map {
           it.selected = it.item == result.playerAObject.item
        }

        gameItemsPlayerB.map {
            it.selected = it.item == result.playerBObject.item
        }

        resultLiveData.postValue(ResultUI(result.state))
    }

    fun getRandomNumber(): Int{
        return (gameObjects.indices).shuffled().first()
    }


}