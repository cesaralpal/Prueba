package com.villas.advertisingApp.bluetoothConnection

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.villas.advertisingApp.R
import com.villas.advertisingApp.config.AdvertisingAplication
import com.villas.advertisingApp.util.isConnected
import com.villas.advertisingApp.util.showSnackbarShort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_example2.*

private const val EXTRA_MAC_ADDRESS = "extra_mac_address"

class ConnectionExampleActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context, macAddress: String) =
            Intent(context, ConnectionExampleActivity::class.java).apply {
                putExtra(EXTRA_MAC_ADDRESS, macAddress)
            }
    }

    private lateinit var bleDevice: RxBleDevice

    private var connectionDisposable: Disposable? = null

    private var stateDisposable: Disposable? = null

    private val mtuDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)
        connect_toggle.setOnClickListener { onConnectToggleClick() }
        set_mtu.setOnClickListener { onSetMtu() }

        val macAddress = intent.getStringExtra(EXTRA_MAC_ADDRESS)
        title = getString(R.string.mac_address, macAddress)
        bleDevice = AdvertisingAplication.rxBleClient.getBleDevice(macAddress)

        // How to listen for connection state changes
        // Note: it is meant for UI updates only — one should not observeConnectionStateChanges() with BLE connection logic
        bleDevice.observeConnectionStateChanges()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onConnectionStateChange(it) }
            .let { stateDisposable = it }


    }

    private fun onConnectToggleClick() {
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

    @TargetApi(21 /* Build.VERSION_CODES.LOLLIPOP */)
    private fun onSetMtu() {
        newMtu.text.toString().toIntOrNull()?.let { mtu ->
            bleDevice.establishConnection(false)
                .flatMapSingle { rxBleConnection -> rxBleConnection.requestMtu(mtu) }
                .take(1) // Disconnect automatically after discovery
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { updateUI() }
                .subscribe({ onMtuReceived(it) }, { onConnectionFailure(it) })
                .let { mtuDisposable.add(it) }
        }
    }

    private fun onConnectionFailure(throwable: Throwable) = showSnackbarShort("Connection error: $throwable")

    private fun onConnectionReceived() = showSnackbarShort("Connection received")

    private fun onConnectionStateChange(newState: RxBleConnection.RxBleConnectionState) {
        connection_state.text = newState.toString()
        updateUI()
    }

    private fun onMtuReceived(mtu: Int) = showSnackbarShort("MTU received: $mtu")

    private fun dispose() {
        connectionDisposable = null
        updateUI()
    }

    private fun triggerDisconnect() = connectionDisposable?.dispose()

    private fun updateUI() {
        connect_toggle.setText(if (bleDevice.isConnected) R.string.button_disconnect else R.string.button_connect)
        autoconnect.isEnabled = !bleDevice.isConnected
    }

    override fun onPause() {
        super.onPause()
        triggerDisconnect()
        mtuDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        stateDisposable?.dispose()
    }
}