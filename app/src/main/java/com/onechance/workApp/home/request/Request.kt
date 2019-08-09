package com.onechance.workApp.home.request

import android.content.Context
import com.onechance.workApp.constants.Web
import com.onechance.workApp.data.remote.Servicelist
import com.onechance.workApp.home.interfaces.IHome
import com.onechance.workApp.model.Respuesta
import com.polimentes.utilitieskotlin.interfaces.weberrorlistener.IWebErrorListener
import com.polimentes.utilitieskotlin.retrofit.NoInternetException
import com.polimentes.utilitieskotlin.retrofit.RetrofitFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class Request @Inject constructor(val context: Context) : IHome.Request {

    private lateinit var requestListener: IHome.RequestListener
    private lateinit var webErrorListener: IWebErrorListener
    private val serviceJson: Servicelist =
        RetrofitFactory.makeService(Web.URL_BASE, Servicelist::class.java, context)


    override fun attachListeners(requestListener: IHome.RequestListener, webErrorListener: IWebErrorListener) {
        this.requestListener = requestListener
        this.webErrorListener = webErrorListener
    }


//    override fun getJson() {
//            serviceJson.getJson("https://io.adafruit.com/api/v2/${App.grupo}/groups/$url/feeds/grafica-voltaje/data")
//                .subscribeOn(Schedulers.io())
//                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//                .subscribe(
//                    { response ->
//                        val jsonResponse = JSONArray(response.string())
//                        val list: ArrayList<Respuesta>? = ArrayList()
//
//                        for (i in 0 until  100){
//                            val json = jsonResponse.getJSONObject(i)
//                            list?.add(
//                                Respuesta(json.getString("created_at"), json.getDouble("value"),"voltaje"))
//                        }
//
//
//                        requestListener.onUserInfoRetrived(list)
//                    },
//                    { errorResponse ->
//                        when (errorResponse) {
//                            //is HttpException -> requestListener.onSuccesListCards(null, Status.FAILED, RequestErrorType.EXCEPTION, errorResponse.code(), errorResponse.message())
//                            is SocketTimeoutException -> webErrorListener.onTimeoutError(errorResponse.message)
//                            is NoInternetException -> webErrorListener.onNotInternetConnection(errorResponse.message)
//                            is IOException -> webErrorListener.onNotReachableServer(errorResponse.message)
//                        }
//                    }
//                )
//        }

//    (0 until items.length())
//    .map { index -> items.getJSONObject(index) }
//    .mapTo(list) { item ->
//        Plan(
//            item.getString(Json.LABEL_ID),
//            item.getInt(Json.LABEL_DAYS),
//            item.getString(Json.LABEL_STATUS),
//            item.getString(Json.LABEL_START_DATE),
//            item.getString(Json.LABEL_FINISH_DATE),
//            item.getString(Json.LABEL_PACKAGE_NAME)
//        )
//    }

}