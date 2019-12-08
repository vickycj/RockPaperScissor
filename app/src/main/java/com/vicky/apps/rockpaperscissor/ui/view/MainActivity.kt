package com.vicky.apps.rockpaperscissor.ui.view
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vicky.apps.gamecore.GameEngineCore
import com.vicky.apps.gamecore.GameType
import com.vicky.apps.gamecore.Result
import com.vicky.apps.gamecore.ResultCallback
import com.vicky.apps.rockpaperscissor.base.AppConstants
import com.vicky.apps.rockpaperscissor.base.BaseActivity
import com.vicky.apps.rockpaperscissor.common.ViewModelProviderFactory
import com.vicky.apps.rockpaperscissor.ui.adapter.DataAdapter

import com.vicky.apps.rockpaperscissor.ui.viewmodel.MainViewModel
import javax.inject.Inject


class MainActivity : BaseActivity() {



    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel:MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vicky.apps.rockpaperscissor.R.layout.activity_main)
        initializeValues()


        val intent = Intent(this,GameActivity::class.java)
        intent.putExtra(AppConstants.GAME_TYPE_INTENT,GameType.PLAYER_VS_COMPUTER)
        startActivity(intent)
    }



    private fun initializeValues() {

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.setCompositeData(compositeDisposable)

    }








}
