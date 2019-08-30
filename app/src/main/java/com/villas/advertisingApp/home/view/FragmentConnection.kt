package com.villas.advertisingApp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.villas.advertisingApp.R
import com.villas.advertisingApp.home.interfaces.IFragmentConnection

class FragmentConnection:Fragment(),IFragmentConnection.View{

    companion object{
        fun newInstance(): Fragment {
            return FragmentConnection()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mode_connection, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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