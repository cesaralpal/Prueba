package com.polimentes.utilitieskotlin.interfaces.listener

import androidx.fragment.app.DialogFragment


interface IBuilderDialogListener {
    interface Activity {
        fun onSuccessBuiltDialog(dialog: DialogFragment, tag: String)
    }

    interface Fragment {
        fun onSuccessBuiltDialog(dialog: DialogFragment, tag: String, requestCode: Int)
    }
}