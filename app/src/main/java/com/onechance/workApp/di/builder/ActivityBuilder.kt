package com.onechance.workApp.di.builder


import com.onechance.workApp.home.HomeModule
import com.onechance.workApp.home.view.Home
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(HomeModule::class)])
    abstract fun bindHomeActivity(): Home


}