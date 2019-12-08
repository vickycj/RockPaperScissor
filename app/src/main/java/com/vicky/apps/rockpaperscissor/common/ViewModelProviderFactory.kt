package com.vicky.apps.rockpaperscissor.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vicky.apps.gamecore.GamePlay
import com.vicky.apps.rockpaperscissor.data.remote.Repository
import com.vicky.apps.rockpaperscissor.ui.viewmodel.GameViewModel
import com.vicky.apps.rockpaperscissor.ui.viewmodel.MainViewModel
import javax.inject.Inject


class ViewModelProviderFactory @Inject constructor(val gamePlay: GamePlay) :
    ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

            return MainViewModel() as T
        }else if(modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(gamePlay) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName())
    }
}