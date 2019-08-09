package com.onechance.workApp.splash.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.onechance.workApp.R
import com.onechance.workApp.constants.App
import com.onechance.workApp.splash.interfaces.ISplash
import com.onechance.workApp.splash.presenter.SplashPresenter
import com.polimentes.utilitieskotlin.Util

class SplashActivity : AppCompatActivity(),ISplash.View {

    private val presenter: ISplash.Presenter by lazy {
        SplashPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setData()
        setListeners()
    }

    override fun setData() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            presenter.splashShowed()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                App.REQUEST_PERMISSION_LOCATION
            )
        }
    }

    override fun setListeners() {
    }


    override fun showMessage(messageResource: Any) {
        Util.showMessage(this, messageResource)

    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
        Util.redirectTo(this, classToRedirect, flags, params, finish)
        finish()
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            App.REQUEST_PERMISSION_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.splashShowed()
                } else {
                    presenter.splashShowed()
                }
            }
        }


    }
}