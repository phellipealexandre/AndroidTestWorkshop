package com.thoughtworks.pafsilva.testautomationworkshop.model

import java.io.Serializable

data class Geo(
    val lat: String,
    val lng: String
) : Serializable