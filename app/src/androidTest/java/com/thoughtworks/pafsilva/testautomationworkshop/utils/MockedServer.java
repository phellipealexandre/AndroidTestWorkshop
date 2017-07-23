package com.thoughtworks.pafsilva.testautomationworkshop.utils;

import fi.iki.elonen.NanoHTTPD;

public class MockedServer extends NanoHTTPD {

    private final static int PORT = 4444;
    private String response;

    public MockedServer() {
        super(PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return new Response(response);
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
