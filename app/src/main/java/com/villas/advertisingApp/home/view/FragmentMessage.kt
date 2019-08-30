package com.villas.advertisingApp.home.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.polidea.rxandroidble2.RxBleConnection
import com.villas.advertisingApp.R
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.constants.App
import com.villas.advertisingApp.home.interfaces.IFragmentConnection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_message.*
import java.util.*

class FragmentMessage: Fragment(), IFragmentConnection.View{

    val rxBleClient = AdvertisingAplication.rxBleClient


    private val DEVICE_CALLBACK_0 = UUID.randomUUID()

    private val DEVICE_CALLBACK_1 = UUID.randomUUID()

    private val WRITE_CHARACTERISTIC = UUID.randomUUID()
    companion object{

        fun newInstance(arguments:Bundle?):Fragment{
            val fragment = FragmentMessage()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
    }

    override fun setData() {
    }

    override fun setListeners() {
        btnSendMessage.setOnClickListener {

                if (arguments!!.containsKey(App.TAG_FRAGMENT_MAC)) {
                    Log.d(App.TAG_LOG, arguments!!.getString(App.TAG_FRAGMENT_MAC))
                    val MAC = arguments!!.getString(App.TAG_FRAGMENT_MAC)
                    rxBleClient.getBleDevice(MAC)




                } else {
                    Log.d(App.TAG_LOG, "no contengo MAC")

                }
            }
    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {

    }

    private fun setupNotifications(
        connection: RxBleConnection
    ): Observable<Pair<Observable<ByteArray>, Observable<ByteArray>>> =
        Observables.combineLatest(
            connection.setupNotification(DEVICE_CALLBACK_0),
            connection.setupNotification(DEVICE_CALLBACK_1)
        ) { deviceCallback0, deviceCallback1 -> deviceCallback0 to deviceCallback1 }

}