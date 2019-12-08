package com.vicky.apps.rockpaperscissor.di

import android.app.Application
import com.vicky.apps.rockpaperscissor.base.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class, AppModule::class,
    ActivityBuilder::class))
interface AppComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}