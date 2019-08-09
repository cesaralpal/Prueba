package com.onechance.workApp.home.presenter

import com.onechance.workApp.home.interfaces.IHome
import com.onechance.workApp.model.Respuesta

class Presenter(val interactor: IHome.Interactor): IHome.Presenter,IHome.Listener {


    /**
 * Métodos del presenter
 * */
    lateinit var view: IHome.View
    override fun attachView(view: IHome.View) {
        this.view = view
    }

    override fun detachView() {
    }

    override fun getJson() {
        interactor.getJson()
    }

    override fun setJson(lista: ArrayList<Respuesta?>) {
        view.setAdapter(lista)
    }

    override fun onNotInternetConnection() {
        view.showMessage("Error de conexión")

    }

    override fun onWebError(message: Any) {
        view.showMessage(message)
    }




}