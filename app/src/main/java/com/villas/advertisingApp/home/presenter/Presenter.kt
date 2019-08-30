package com.villas.advertisingApp.home.presenter

import com.villas.advertisingApp.home.interfaces.IHome
import com.villas.advertisingApp.model.Respuesta

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

    }

    override fun onNotInternetConnection() {
        view.showMessage("Error de conexión")

    }

    override fun onWebError(message: Any) {
        view.showMessage(message)
    }




}