package com.villas.advertisingApp.home

import com.villas.advertisingApp.home.interactor.Interactor
import com.villas.advertisingApp.home.interfaces.IHome
import com.villas.advertisingApp.home.presenter.Presenter
import com.villas.advertisingApp.home.request.Request
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {
    @Binds
    abstract fun bindHomePresenter(presenter: Presenter): IHome.Presenter

    @Binds
    abstract fun bindHomeInteractor(interactor: Interactor): IHome.Interactor

    @Binds
    abstract fun bindHomeRequest(request: Request): IHome.Request

}