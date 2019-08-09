package com.onechance.workApp.home.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.onechance.workApp.R
import com.onechance.workApp.home.interfaces.IHome
import com.onechance.workApp.model.Respuesta
import com.polimentes.utilitieskotlin.Util
import dagger.android.AndroidInjection

class Home: AppCompatActivity(),IHome.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setData()

    }

    override fun setAdapter(lista:ArrayList<Respuesta?>) {

    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
        Util.redirectTo(this,classToRedirect,flags,params,finish)
    }

    override fun setData() {
    }

    override fun setListeners() {
    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }


}