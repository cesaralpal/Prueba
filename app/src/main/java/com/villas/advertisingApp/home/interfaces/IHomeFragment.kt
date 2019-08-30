package com.villas.advertisingApp.home.interfaces

import androidx.fragment.app.Fragment
import com.polimentes.utilitieskotlin.interfaces.view.IFragmentView
import com.polimentes.utilitieskotlin.interfaces.view.IView

interface IHomeFragment{

    interface View: IFragmentView,IView{

        fun starScanner()
        fun setAdapter()
        fun getBluetoothPermission()
        override fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String)

    }



}