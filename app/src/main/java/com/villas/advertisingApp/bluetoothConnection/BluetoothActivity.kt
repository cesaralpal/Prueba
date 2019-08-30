package com.villas.advertisingApp.bluetoothConnection

import android.annotation.TargetApi
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import com.polimentes.utilitieskotlin.Util
import com.villas.advertisingApp.bluetoothConnection.interfaces.IBluetooth
import com.villas.advertisingApp.R
import com.villas.advertisingApp.bluetoothConnection.presenter.ScanResultsAdapter
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.constants.App
import com.villas.advertisingApp.home.view.Home
import com.villas.advertisingApp.util.*
import com.villas.advertisingApp.util.isConnected
import com.villas.advertisingApp.util.isLocationPermissionGranted
import com.villas.advertisingApp.util.requestLocationPermission
import com.villas.advertisingApp.util.showError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_example1.*
import kotlinx.android.synthetic.main.activity_example2.*



class BluetoothActivity : AppCompatActivity(), IBluetooth.View {

    private var connectionDisposable: Disposable? = null

    private var stateDisposable: Disposable? = null

    private val mtuDisposable = CompositeDisposable()

    private lateinit var bleDevice: RxBleDevice

    companion object {
     //   fun newInstance() = FragmentListScanner()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)
        setData()
//        if (getBundle().containsKey(App.TAG_FRAGMENT_MAC)){
//            val MAC = getBundle().getString(App.TAG_FRAGMENT_MAC)
//            bleDevice = rxBleClient.getBleDevice(MAC)
//            bleDevice.observeConnectionStateChanges()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {    triggerDisconnect() }
//                .let { stateDisposable = it
//
//                }
//
    }

    override fun setData() {
        configureResultList()
        setListeners()

    }
    override fun getBundle(): Bundle {
        return intent.extras ?: Bundle()
    }
     override fun setListeners() {
        scan_toggle_btn.setOnClickListener { onScanToggleClick() }

    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
        Util.redirectTo(this,classToRedirect,flags,params,finish)
    }

    private val rxBleClient = AdvertisingAplication.rxBleClient
    var uuid: ParcelUuid = ParcelUuid.fromString("00001901-0000-1000-8000-00805f9b34fb")
    private var scanDisposable: Disposable? = null

    private val resultsAdapter =
        ScanResultsAdapter {
          Log.d("bletry","bletry${it.bleDevice.macAddress}")

            bleDevice = rxBleClient.getBleDevice(it.bleDevice.macAddress)


            if (bleDevice.isConnected) {
                triggerDisconnect()
            } else {

                bleDevice.establishConnection(false)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { dispose() }
                    .subscribe({ onConnectionReceived() }, { onConnectionFailure(it) })
                    .let { connectionDisposable = it }
            }
        }

    private var hasClickedScan = false

    private val isScanning: Boolean
    get() = scanDisposable != null


    private fun onConnectionFailure(throwable: Throwable) = showSnackbarShort("Connection error: $throwable")

    private fun onConnectionReceived() {

        showSnackbarShort("Connection received")
        val params = Bundle()
        params.putInt(App.TAG_FRAGMENT_MESSAGE, App.REQUEST_CODE_FRAGMENT_MESSAGE)
        params.putString(App.TAG_FRAGMENT_MAC,bleDevice.macAddress)
        redirectTo(Home::class.java,null,params,true)
    }
    private fun triggerDisconnect() = connectionDisposable?.dispose()


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
            if (isLocationPermissionGranted()) {
                scanBleDevices()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { dispose() }
                    .subscribe({
                        resultsAdapter.addScanResult(it)

                    }, { onScanFailure(it) })
                    .let { scanDisposable = it }
            } else {
                hasClickedScan = true
              requestLocationPermission()
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
        if (throwable is BleScanException) showError(throwable)
    }

    private fun updateButtonUIState() =
        scan_toggle_btn.setText(if (isScanning) R.string.button_stop_scan else R.string.button_start_scan)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isLocationPermissionGranted(requestCode, grantResults) && hasClickedScan) {
            hasClickedScan = false
            scanBleDevices()
        }
    }

    private fun onConnectionStateChange(newState: RxBleConnection.RxBleConnectionState) {

    }
    private fun updateUI() {
    }
    public override fun onPause() {
        super.onPause()
        // Stop scanning in onPause callback.
        if (isScanning) scanDisposable?.dispose()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        redirectTo(Home::class.java,null,null,true)
    }
}