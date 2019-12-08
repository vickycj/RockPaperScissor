package com.vicky.apps.rockpaperscissor.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vicky.apps.gamecore.*
import com.vicky.apps.rockpaperscissor.ui.model.GameItems
import com.vicky.apps.rockpaperscissor.ui.model.ResultUI


class GameViewModel(val gamePlay: GamePlay) : ViewModel() {

    private var gameObjects: List<GameObject> = arrayListOf()

    var gameItemsPlayerA: List<GameItems> = arrayListOf()
    var gameItemsPlayerB: List<GameItems> = arrayListOf()

    val progressLiveData: MutableLiveData<Long> = MutableLiveData()

    val resultLiveData: MutableLiveData<ResultUI> = MutableLiveData()

    fun initializeGame(gameType: GameType) {
        gamePlay.initializeGame(gameType, object : ResultCallback {
            override fun onProgress(progress: Long) {
                onProgress(progress)
            }

            override fun onResult(result: Result) {
                onResult(result)
            }

        })

        gameObjects = gamePlay.getListOfGameObjects()

        gameItemsPlayerA = populateGameObjects()
        gameItemsPlayerB = populateGameObjects()
    }

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
    }

    fun resetGame(){
        gameItemsPlayerA.map {
            it.selected = false
        }

        gameItemsPlayerB.map {
            it.selected = false
        }
    }

    private fun onProgress(onProgress: Long) {
        progressLiveData.postValue(onProgress)
    }

    private fun onResult(result: Result) {
       gameItemsPlayerA.map {
           it.selected = it.item == result.playerAObject.item
        }

        gameItemsPlayerB.map {
            it.selected = it.item == result.playerBObject.item
        }

        resultLiveData.postValue(ResultUI(result.state))
    }


}