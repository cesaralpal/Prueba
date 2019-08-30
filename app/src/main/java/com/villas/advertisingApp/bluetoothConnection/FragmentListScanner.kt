package com.villas.advertisingApp.bluetoothConnection

import android.os.Build
import android.os.Bundle
import android.os.ParcelUuid
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import com.villas.advertisingApp.R
import com.villas.advertisingApp.bluetoothConnection.presenter.ScanResultsAdapter
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.home.interfaces.IHomeFragment
import com.villas.advertisingApp.util.isLocationPermissionGranted
import com.villas.advertisingApp.util.requestLocationPermission
import com.villas.advertisingApp.util.showError
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_example1.scan_results
import kotlinx.android.synthetic.main.activity_example1.scan_toggle_btn

class FragmentListScanner : Fragment(), IHomeFragment.View {

    companion object {
        fun newInstance() = FragmentListScanner()
    }


    override fun starScanner() {

    }

    override fun setAdapter() {
    }

    override fun getBluetoothPermission() {
    }

    override fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String) {
    }

    override fun setData() {
        setListeners()
    }

    override fun setListeners() {
        scan_toggle_btn.setOnClickListener { onScanToggleClick() }

    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
    }

    private val rxBleClient = AdvertisingAplication.rxBleClient
    var uuid: ParcelUuid = ParcelUuid.fromString("00001901-0000-1000-8000-00805f9b34fb")
    private var scanDisposable: Disposable? = null

    private val resultsAdapter =
        ScanResultsAdapter {
            startActivity(
                DeviceActivity.newInstance(
                    activity!!,
                    it.bleDevice.macAddress
                )
            )
        }

    private var hasClickedScan = false

    private val isScanning: Boolean
        get() = scanDisposable != null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(R.layout.activity_example1, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
        configureResultList()


    }
    private fun configureResultList() {
        with(scan_results) {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = resultsAdapter
        }
    }

    private fun onScanToggleClick() {
        if (isScanning) {
            scanDisposable?.dispose()
        } else {
            if (activity!!.isLocationPermissionGranted()) {
                scanBleDevices()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { dispose() }
                    .subscribe({ resultsAdapter.addScanResult(it)

                    }, { onScanFailure(it) })
                    .let { scanDisposable = it }
            } else {
                hasClickedScan = true
                activity!!.requestLocationPermission()
            }
        }
        updateButtonUIState()
    }

    private fun scanBleDevices(): Observable<ScanResult> {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(uuid)
//            .setDeviceAddress("B4:99:4C:34:DC:8B")
            // add custom filters if needed
            .build()

        return rxBleClient.scanBleDevices(scanSettings, scanFilter)
    }

    private fun dispose() {
        scanDisposable = null
        resultsAdapter.clearScanResults()
        updateButtonUIState()
    }

    private fun onScanFailure(throwable: Throwable) {
        if (throwable is BleScanException) activity!!.showError(throwable)
    }

    private fun updateButtonUIState() =
        scan_toggle_btn.setText(if (isScanning) R.string.button_stop_scan else R.string.button_start_scan)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isLocationPermissionGranted(requestCode, grantResults) && hasClickedScan) {
            hasClickedScan = false
            scanBleDevices()
        }
    }

    public override fun onPause() {
        super.onPause()
        // Stop scanning in onPause callback.
        if (isScanning) scanDisposable?.dispose()
    }
}