package com.vicky.apps.rockpaperscissor.ui.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vicky.apps.rockpaperscissor.R
import com.vicky.apps.rockpaperscissor.base.BaseActivity
import com.vicky.apps.rockpaperscissor.common.ViewModelProviderFactory
import com.vicky.apps.rockpaperscissor.ui.viewmodel.GameViewModel
import javax.inject.Inject

class GameActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(GameViewModel::class.java)
    }


}
