package com.villas.advertisingApp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.villas.advertisingApp.home.interfaces.IFragmentConnection

class FragmentHistory: Fragment(), IFragmentConnection.View{

    companion object{
        fun newInstance(): Fragment {
            return FragmentHistory()
        }
    }

    override fun setData() {
    }

    override fun setListeners() {
    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

}