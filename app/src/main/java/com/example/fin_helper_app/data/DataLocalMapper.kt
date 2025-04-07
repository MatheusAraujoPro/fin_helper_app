package com.example.fin_helper_app.data

abstract class DataLocalMapper<in R, out D> {
    abstract fun toDomain(data: R): D
}