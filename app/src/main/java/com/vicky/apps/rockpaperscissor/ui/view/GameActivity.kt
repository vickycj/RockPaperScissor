package com.vicky.apps.rockpaperscissor.ui.view

import android.os.Bundle
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val gameType:GameType = intent?.
            getSerializableExtra(AppConstants.GAME_TYPE_INTENT) as GameType

        initializeGame(gameType)
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
        viewModel.gameItemsPlayerB[i].selected = true
        playerAdapterB.updateData(viewModel.gameItemsPlayerB)
    }

    private fun clickInPlayerA(gameItems: GameItems, i: Int) {

    }

    private fun initializeGame(gameType: GameType) {
        viewModel.initializeGame(gameType)
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

    private fun onProgress(it: Long?) {

    }

    private fun onResult(resultUI: ResultUI?) {

    }


}
