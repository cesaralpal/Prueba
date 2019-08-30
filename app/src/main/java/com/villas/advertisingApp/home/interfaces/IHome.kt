package com.villas.advertisingApp.home.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.villas.advertisingApp.constants.Web
import com.villas.advertisingApp.model.Respuesta
import com.polimentes.utilitieskotlin.di.base.IBaseInteractor
import com.polimentes.utilitieskotlin.di.base.IBasePresenter
import com.polimentes.utilitieskotlin.di.base.IBaseRequest
import com.polimentes.utilitieskotlin.di.base.IBaseView
import com.polimentes.utilitieskotlin.enums.RequestErrorType
import com.polimentes.utilitieskotlin.enums.Status
import com.polimentes.utilitieskotlin.interfaces.listener.IWebListener
import com.polimentes.utilitieskotlin.interfaces.view.IView
import com.polimentes.utilitieskotlin.interfaces.weberrorlistener.IWebErrorListener

interface IHome {
    interface View : IView, IBaseView {
        fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String)
        fun getBundle(): Bundle

    }

    interface Presenter : IBasePresenter<View> {
        fun getJson()
    }

    interface Listener : IWebListener {
        fun setJson(lista:ArrayList<Respuesta?>)

    }

    interface Interactor : IBaseInteractor<Listener> {
        fun getJson()
    }

    interface Request : IBaseRequest<RequestListener, IWebErrorListener> {
        fun getJson()
    }

    interface RequestListener {
        fun onUserInfoRetrived(payload: ArrayList<Respuesta?>, status: Status? = Status.OK,
                               errorType: RequestErrorType? = RequestErrorType.NONE,
                               code: Int = Web.RESPONSE_OK, message: String? = null)
    }
}