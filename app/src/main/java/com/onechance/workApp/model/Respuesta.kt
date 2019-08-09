package com.onechance.workApp.model

class Respuesta(var description:String = "",var name:String="") {
    override fun toString(): String {
        return "Respuesta(description='$description', name='$name')"
    }
}