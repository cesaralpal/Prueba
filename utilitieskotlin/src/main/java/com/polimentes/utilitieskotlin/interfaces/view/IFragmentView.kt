package com.polimentes.utilitieskotlin.interfaces.view

import androidx.fragment.app.Fragment


interface IFragmentView : ISimpleView {
    fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String)
}