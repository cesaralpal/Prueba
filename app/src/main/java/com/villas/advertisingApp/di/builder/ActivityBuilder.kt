package com.villas.advertisingApp.di.builder


import com.villas.advertisingApp.home.HomeModule
import com.villas.advertisingApp.home.view.Home
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(HomeModule::class)])
    abstract fun bindHomeActivity(): Home


}