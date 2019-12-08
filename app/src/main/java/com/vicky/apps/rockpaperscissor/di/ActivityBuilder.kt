package com.vicky.apps.rockpaperscissor.di

import com.vicky.apps.rockpaperscissor.ui.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}