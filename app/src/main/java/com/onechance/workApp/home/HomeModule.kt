package com.onechance.workApp.home

import com.onechance.workApp.home.interactor.Interactor
import com.onechance.workApp.home.interfaces.IHome
import com.onechance.workApp.home.presenter.Presenter
import com.onechance.workApp.home.request.Request
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