package com.thoughtworks.pafsilva.testautomationworkshop.utils

import fi.iki.elonen.NanoHTTPD

class MockedServer : NanoHTTPD(PORT) {

    companion object {
        private const val PORT = 4444
    }

    private var response: String? = null

    override fun serve(session: IHTTPSession): Response {
        return Response(response)
    }

    fun setResponse(response: String) {
        this.response = response
    }
}
