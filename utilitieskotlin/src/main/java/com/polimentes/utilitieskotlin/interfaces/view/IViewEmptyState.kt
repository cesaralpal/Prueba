package com.polimentes.utilitieskotlin.interfaces.view

/**
 * @author Miguel A. Polimentes
 *         Description:
 *         created on 24/09/2018
 */
interface IViewEmptyState {
    /**
     * Despliega una vista al no obtener datos.
     * @param isVisible: Bandera que determina la visibilidad.
     */
    fun setVisibilityEmptyStateInternet(isVisible: Boolean)
    fun setVisibilityEmptyStatePayload(isVisible: Boolean)
}