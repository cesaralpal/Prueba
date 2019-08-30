package com.villas.advertisingApp.bluetoothConnection

import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.villas.advertisingApp.R
import com.villas.advertisingApp.home.interfaces.IHomeFragment
import android.util.Log
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.util.*
import com.villas.advertisingApp.util.isLocationPermissionGranted
import com.villas.advertisingApp.util.requestLocationPermission
import com.villas.advertisingApp.util.showSnackbarShort
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_scannerble.*


class FragmentScanner :  Fragment(), IHomeFragment.View {

    private val rxBleClient = AdvertisingAplication.rxBleClient

    private lateinit var callbackIntent: PendingIntent

    private var hasClickedScan = false

    override fun starScanner() {

    }

    override fun setAdapter() {
    }

    override fun getBluetoothPermission() {
    }

    override fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String) {
    }

    override fun setData() {
    }



    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
    }

    companion object {
        fun newInstance() = FragmentScanner()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(R.layout.fragment_scannerble, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
        callbackIntent = ScanReceiver.newPendingIntent(activity!!)


    }


    override fun setListeners(){
        startScanner.setOnClickListener { onScanStartClick() }
        stopScanner.setOnClickListener { onScanStopClick() }
    }


    private fun onScanStartClick() {
        if (activity!!.isLocationPermissionGranted()) {
            scanBleDeviceInBackground()
        } else {
            hasClickedScan = true
            activity!!.requestLocationPermission()
        }
    }

    private fun scanBleDeviceInBackground() {
        if (Build.VERSION.SDK_INT >= 26 /* Build.VERSION_CODES.O */) {
            try {
                val scanSettings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .build()

                val scanFilter = ScanFilter.Builder()
//                    .setDeviceAddress("5C:31:3E:BF:F7:34")
                    // add custom filters if needed
                    .build()

                rxBleClient.backgroundScanner.scanBleDeviceInBackground(callbackIntent, scanSettings, scanFilter)

            } catch (scanException: BleScanException) {
                Log.e("BackgroundScanActivity", "Failed to start background scan", scanException)
                activity!!.showError(scanException)
            }
        } else {
           activity!!.showSnackbarShort("Background scanning requires at least API 26")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isLocationPermissionGranted(requestCode, grantResults) && hasClickedScan) {
            hasClickedScan = false
            scanBleDeviceInBackground()
        }
    }

    private fun onScanStopClick() {
        if (Build.VERSION.SDK_INT >= 26 /* Build.VERSION_CODES.O */) {
            val lista = rxBleClient.backgroundScanner.stopBackgroundBleScan(callbackIntent)

        Log.d("blelist",lista.toString())
        } else {
            activity!!.showSnackbarShort("Background scanning requires at least API 26")
        }
    }
}
