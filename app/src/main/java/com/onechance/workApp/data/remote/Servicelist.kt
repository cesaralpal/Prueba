package com.onechance.workApp.data.remote

import com.onechance.workApp.constants.Web
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface Servicelist {

    @GET
    fun getJson(@Url url: String): Flowable<ResponseBody>
}