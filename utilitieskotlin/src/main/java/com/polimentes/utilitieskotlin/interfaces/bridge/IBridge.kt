package com.polimentes.utilitieskotlin.interfaces.bridge

import android.os.Bundle

/**
 * Interfaz implementada por activity para la comunicación de fragment a activity
 */
interface IBridge {
    interface FromFragment {
        /**
         * Método utilizado por un fragment para comunicar informacion al activity contenedor
         * que debe ser guardada, utilizada o redirigida
         * @param data: información de interés
         * @param tag: etiqueta del fragment al que se desea transferir información
         */
        fun onDataRetrieved(data: Bundle?, tag: String? = null)
    }

    interface FromActivity {
        /**
         * Método utilizado por un activity para comunicar informacion al fragment contenido
         * que debe ser guardada, utilizada o redirigida
         * @param data: información de interés
         */
        fun onDataRetrieved(data: Bundle?)
    }


}