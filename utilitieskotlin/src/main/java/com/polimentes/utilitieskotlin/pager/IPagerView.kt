package com.polimentes.utilitieskotlin.pager

import android.app.FragmentManager

interface IPagerView {
    /**
     * Método encargado de construir el pager
     * Responsable de agregar las páginas a mostrar
     */
    fun buildPager()

}