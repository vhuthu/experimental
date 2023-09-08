package com.vhuthu.work.models

data class Farms(
    val address: String,
    val assets: List<Any>,
    val crops: List<Any>,
    val farmAddress: String,
    val farmName: String,
    val farmingReason: String,
    val id: Int,
    val incomeStatements: List<Any>,
    val numberOfEmployees: Int,
    val plots: List<Any>,
    val yearsActive: Int
)