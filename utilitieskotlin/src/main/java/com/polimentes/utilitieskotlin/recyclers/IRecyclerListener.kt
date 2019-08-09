package com.polimentes.utilitieskotlin.recyclers



interface IRecyclerListener<T> {
    fun onItemClick(item: T)

    interface Complex<T, U> {
        fun onItemClick(item: T, companion: U)
    }
}