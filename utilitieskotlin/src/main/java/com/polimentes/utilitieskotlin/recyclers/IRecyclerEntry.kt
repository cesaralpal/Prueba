package com.polimentes.utilitieskotlin.recyclers

import java.util.*


interface IRecyclerEntry<T> {
    fun addItem(item: T)
    fun addList(list: ArrayList<T>)
    fun deleteItem(position: Int)

}