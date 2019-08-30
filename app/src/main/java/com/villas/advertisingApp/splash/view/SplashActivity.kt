package com.villas.advertisingApp.splash.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.villas.advertisingApp.R
import com.villas.advertisingApp.constants.App
import com.villas.advertisingApp.splash.interfaces.ISplash
import com.villas.advertisingApp.splash.presenter.SplashPresenter
import com.polimentes.utilitieskotlin.Util
import android.bluetooth.BluetoothAdapter
import android.content.Intent



class SplashActivity : AppCompatActivity(),ISplash.View {

    private var logo: ImageView? = null
    private val splashTimeOut = 3000f
    private val presenter: ISplash.Presenter by lazy {
        SplashPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logo=findViewById(R.id.logo)

        Handler().postDelayed(Runnable {
            setData()
            //finish()
        }, splashTimeOut.toLong())

        val myanim = AnimationUtils.loadAnimation(this, R.anim.mysplasanimation)
        logo!!.startAnimation(myanim)
    }

    override fun setData() {


            presenter.splashShowed()
    }

    override fun setListeners() {
    }




    override fun showMessage(messageResource: Any) {
        Util.showMessage(this, messageResource)

    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
        Util.redirectTo(this, classToRedirect, flags, params,finish)
        finish()
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return

        when (requestCode) {
            App.REQUEST_PERMISSION_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.splashShowed()
                }
            }
        }
    }
}