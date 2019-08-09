package com.polimentes.utilitieskotlin.dialogs

import com.polimentes.utilitieskotlin.enums.PerformBy

interface IDialogView {
    fun hideDialog(performBy: PerformBy)
    fun setData()
    fun setListeners()
    fun setBody(body: Any)
    fun setTitle(title: Any)
    fun setImage(image: Int)

}