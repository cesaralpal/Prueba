package com.polimentes.utilitieskotlin.retrofit

import java.io.IOException


class NoInternetException : IOException() {
    override fun getLocalizedMessage(): String {
        return "No connectivity exception"
    }

    override val message: String?
        get() = "No connectivity exception"
}