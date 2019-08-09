package com.polimentes.utilitieskotlin.interfaces.view

import android.content.Intent

interface IFragmentResult : IFragmentView {

    fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?)
}