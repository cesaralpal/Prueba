package com.polimentes.utilitieskotlin.dialogs



interface IDialogListener {
    interface Simple {
        fun onAcceptClickListener(tag: String)
        fun onDialogCanceled(tag: String)
    }

    interface InputField {
        fun onDataRetrived(data: ArrayList<String>, tag: String)
        fun onDialogCanceled(tag: String)
    }

}