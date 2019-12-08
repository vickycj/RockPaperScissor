package com.vicky.apps.rockpaperscissor.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vicky.apps.gamecore.GameState
import com.vicky.apps.gamecore.GameType
import com.vicky.apps.rockpaperscissor.R
import com.vicky.apps.rockpaperscissor.base.AppConstants
import com.vicky.apps.rockpaperscissor.base.BaseActivity
import com.vicky.apps.rockpaperscissor.common.ViewModelProviderFactory
import com.vicky.apps.rockpaperscissor.ui.adapter.DataAdapter
import com.vicky.apps.rockpaperscissor.ui.model.GameItems
import com.vicky.apps.rockpaperscissor.ui.model.ResultUI
import com.vicky.apps.rockpaperscissor.ui.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import javax.inject.Inject

class GameActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: GameViewModel

    private var gamePlayed: Boolean = false

    private var gameType: GameType? = null

    private lateinit var playerAdapterA : DataAdapter

    private lateinit var playerAdapterB : DataAdapter

    private val recyclerViewA : RecyclerView by lazy {
        playerARecycler
    }

    private val recyclerViewB : RecyclerView by lazy {
        playerBRecycler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initializeViewModel()
        initializeRecyclers()
        initializeButton()
        val gameType:GameType = intent?.
            getSerializableExtra(AppConstants.GAME_TYPE_INTENT) as GameType

        gameType?.let {
            this.gameType = it
            initializeGame(gameType)

        }

        resetGame()
    }

    private fun initializeButton() {
        playButton.setOnClickListener {
            playButtonClicked()
        }

        changeModeButton.setOnClickListener {
            finish()
        }
    }

    private fun enableDisableButton(boolean: Boolean){
        playButton.isEnabled = boolean
        changeModeButton.isEnabled = boolean
    }

    private fun playButtonClicked() {
        if(gamePlayed){
            resetGame()
            gamePlayed = false
            return
        }

        if(gameType == GameType.PLAYER_VS_COMPUTER){
            if(viewModel.getPlayerSelected()){
                startPlay()
            }
        }else {
            startPlay()
        }

    }

    private fun initializeRecyclers() {
        recyclerViewA.layoutManager = GridLayoutManager(this, 3)
        recyclerViewB.layoutManager = GridLayoutManager(this, 3)

        playerAdapterA = DataAdapter(viewModel.gameItemsPlayerA){
            gameItems, i -> clickInPlayerA(gameItems,i)
        }

        playerAdapterB = DataAdapter(viewModel.gameItemsPlayerB){
            gameItems, i -> clickInPlayerB(gameItems,i)
        }

        recyclerViewA.adapter = playerAdapterA

        recyclerViewB.adapter = playerAdapterB
    }

    private fun clickInPlayerB(gameItems: GameItems, i: Int) {
        viewModel.playerSelection(gameItems)
        playerAdapterB.updateData(viewModel.gameItemsPlayerB)
    }

    private fun clickInPlayerA(gameItems: GameItems, i: Int) {

    }

    private fun initializeGame(gameType: GameType) {
        viewModel.initializeGame(gameType)
        updateAdapter()
        updatePlayerText(gameType)

    }



    private fun updateAdapter(){
        playerAdapterA.updateData(viewModel.gameItemsPlayerA)
        playerAdapterB.updateData(viewModel.gameItemsPlayerB)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(GameViewModel::class.java)

        viewModel.resultLiveData.observe(this, Observer {
            onResult(it)
        })

        viewModel.progressLiveData.observe(this, Observer {
            onProgress(it)
        })
    }

    private fun startPlay(){
        enableDisableButton(false)
        viewModel.startGame()
    }

    private fun onProgress(it: Long?) {
        resultText.text = it.toString()
    }

    private fun onResult(result: ResultUI?) {
        when(result?.gameState){
            GameState.PLAYER_A_WON -> playerAWon()
            GameState.PLAYER_B_WON -> playerBWon()
            GameState.DRAW -> draw()
        }

        enableDisableButton(true)
        gamePlayed = true
    }

    private fun resetGame(){
        if(gameType == GameType.PLAYER_VS_COMPUTER){
            resultText.text = getString(R.string.choose_your_move)
        }else {
            resultText.text = getString(R.string.click_play)
        }
        viewModel.resetGame()
        updateAdapter()
    }

    private fun draw() {
        resultText.text = getString(R.string.draw)
    }

    private fun playerBWon() {
        if(gameType == GameType.PLAYER_VS_COMPUTER){
            resultText.text = getString(R.string.you_won)
        }else {
            resultText.text = getString(R.string.computer_b_won)
        }
    }

    private fun playerAWon() {
        if(gameType == GameType.PLAYER_VS_COMPUTER){
            resultText.text = getString(R.string.you_lose)
        }else {
            resultText.text = getString(R.string.computer_a_won)
        }
    }

    private fun updatePlayerText(gameType: GameType) {
        if(gameType == GameType.PLAYER_VS_COMPUTER){
            playerAText.text = getString(R.string.computer)
            playerBText.text = getString(R.string.player)
        }else if (gameType == GameType.COMPUTER_VS_COMPUTER){
            playerAText.text = getString(R.string.computer)
            playerBText.text = getString(R.string.computer)
        }
    }


}
