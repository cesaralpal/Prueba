package com.villas.advertisingApp.splash.presenter

import android.content.Context
import com.villas.advertisingApp.home.view.Home
import com.villas.advertisingApp.splash.interfaces.ISplash

class SplashPresenter (private val view: ISplash.View,
                       private val context: Context
):ISplash.Presenter,ISplash.Listener{
    override fun splashShowed() {
        view.redirectTo(Home::class.java)
    }

    override fun onValidSession() {
    }

    override fun onInvalidSession() {
    }


}