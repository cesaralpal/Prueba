package com.villas.advertisingApp.home.interactor

import android.content.Context
import com.villas.advertisingApp.home.interfaces.IHome
import com.villas.advertisingApp.model.Respuesta
import com.polimentes.utilitieskotlin.enums.RequestErrorType
import com.polimentes.utilitieskotlin.enums.Status
import com.polimentes.utilitieskotlin.interfaces.weberrorlistener.IWebErrorListener
import javax.inject.Inject

class Interactor @Inject constructor(val context: Context, val request: IHome.Request):IHome.Interactor, IHome.RequestListener,IWebErrorListener
{

    lateinit var listener: IHome.Listener

    override fun attachListener(listener: IHome.Listener) {
        this.listener = listener
    }

    init {
        request.attachListeners(this,this)
    }

    override fun getJson() {

    }

    override fun onUserInfoRetrived(
        payload: ArrayList<Respuesta?>,
        status: Status?,
        errorType: RequestErrorType?,
        code: Int,
        message: String?
    ) {
        listener.setJson(payload)
    }


    override fun handleError(code: Int, message: String?) {
        message?.let {
            listener.onNotInternetConnection()
        }

    }

    override fun handleException(code: Int, message: String?) {
        message?.let {
            listener.onWebError(message)
        }
    }

    override fun onTimeoutError(message: String?) {
        message?.let {
            listener.onWebError(message)
        }
    }

    override fun onNotReachableServer(message: String?) {
        message?.let {
            listener.onWebError(message)
        }
    }

    override fun onNotInternetConnection(message: String?) {
        message?.let {
            listener.onWebError(message)
        }
    }

    override fun onCustomError(code: Int, message: String) {
        message?.let {
            listener.onWebError(message)
        }
    }

    override fun onUndefinedError(code: Int, message: String?) {
        message?.let {
            listener.onWebError(message)
        }
    }



}