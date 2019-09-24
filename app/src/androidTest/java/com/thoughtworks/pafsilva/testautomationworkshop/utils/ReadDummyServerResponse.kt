package com.thoughtworks.pafsilva.testautomationworkshop.utils

import androidx.test.InstrumentationRegistry

import java.io.IOException
import java.io.InputStream
import java.util.Scanner

object ReadDummyServerResponse {

    fun readJsonFile(fileName: String): String {
        val jsonFixtureFile = InstrumentationRegistry.getContext().assets.open("files/$fileName")
        val s = Scanner(jsonFixtureFile).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}