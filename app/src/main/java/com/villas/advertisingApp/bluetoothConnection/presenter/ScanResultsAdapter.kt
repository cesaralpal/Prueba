package com.villas.advertisingApp.bluetoothConnection.presenter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polidea.rxandroidble2.scan.ScanResult
import com.villas.advertisingApp.R
import com.villas.advertisingApp.constants.App

class ScanResultsAdapter(
    private val onClickListener: (ScanResult) -> Unit
) : RecyclerView.Adapter<ScanResultsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val device: TextView = itemView.findViewById(R.id.tvBluetoothName)
        val rssi: TextView = itemView.findViewById(R.id.tvBluetoothMAC)
        val checkBox:CheckBox = itemView.findViewById(R.id.chkBluetooth)
        val progressBar:ProgressBar = itemView.findViewById(R.id.progressConnected)
    }

    private val data = mutableListOf<ScanResult>()

    fun addScanResult(bleScanResult: ScanResult) {
        // Not the best way to ensure distinct devices, just for the sake of the demo.
        data.withIndex()
            .firstOrNull { it.value.bleDevice == bleScanResult.bleDevice }
            ?.let {
                // device already in data list => update
                data[it.index] = bleScanResult
                notifyItemChanged(it.index)
            }
            ?: run {
                // new device => add to data list
                with(data) {
                    add(bleScanResult)
                    sortBy { it.bleDevice.macAddress }
                }
                notifyDataSetChanged()
            }
    }

    fun clearScanResults() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(data[position]) {
            holder.device.text = String.format("%s (%s)", bleDevice.macAddress, bleDevice.name)
            holder.rssi.text = String.format("RSSI: %d", rssi)
            holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {

                    Log.d(App.TAG_LOG, "estre a is checked")
                    holder.progressBar.visibility = View.VISIBLE
                    Log.d("conexion", "cambie le check box")
                    onClickListener(this)

                    // notifyItemChanged(adapterPosition)

                } else {
                    holder.progressBar.visibility = View.GONE
                    onClickListener(this)
                    notifyDataSetChanged()

                }

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bluetooth, parent, false)
            .let { ViewHolder(it) }
}