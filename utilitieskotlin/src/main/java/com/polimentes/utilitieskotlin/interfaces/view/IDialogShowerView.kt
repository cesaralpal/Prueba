package com.polimentes.utilitieskotlin.interfaces.view

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


interface IDialogShowerView {
    interface Activity {
        fun showDialog(dialog: DialogFragment, tag: String)
        fun dismissDialog(dialog: DialogFragment?) = dialog?.dismiss()
        fun getDialog(fragmentManager: FragmentManager?, tag: String): DialogFragment? = fragmentManager?.findFragmentByTag(tag) as DialogFragment?
        fun getMFragmentManager():FragmentManager?
    }

    interface Fragment {
        fun showDialog(dialog: DialogFragment, tag: String, requestCode: Int)
        fun dismissDialog(dialog: DialogFragment?) = dialog?.dismiss()
        fun getDialog(fragmentManager: FragmentManager?, tag: String): DialogFragment? = fragmentManager?.findFragmentByTag(tag) as DialogFragment?
        fun getMFragmentManager():FragmentManager?
    }
}