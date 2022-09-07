package com.example.persistencia

import java.io.Serializable

data class User (
    val name: String,
    val address: String,
    val sex: String) : Serializable {
}