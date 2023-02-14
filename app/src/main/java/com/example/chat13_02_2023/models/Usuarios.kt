package com.example.chat13_02_2023.models

data class Usuarios(
    val email: String? = null,
    val perfil: String = email.toString() + "/perfil.jpg",
    val ciudad: String? = null,
)
