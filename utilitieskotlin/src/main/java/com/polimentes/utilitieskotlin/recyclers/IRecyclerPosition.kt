package com.polimentes.utilitieskotlin.recyclers

import android.view.View

interface IRecyclerPosition {
    fun getCurrentItemPosition(view: View, position: Int)
}