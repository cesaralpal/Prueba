package com.villas.advertisingApp.bluetoothConnection.interfaces

import android.os.Bundle
import com.polimentes.utilitieskotlin.interfaces.view.IView

interface IBluetooth {
    interface View : IView {
        fun getBundle(): Bundle
    }
}