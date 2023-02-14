package com.example.chat13_02_2023.models

data class Mensajes(
    val email: String? = null,
    val mensaje: String? = null,
    val fecha: Long? = System.currentTimeMillis(),
)
