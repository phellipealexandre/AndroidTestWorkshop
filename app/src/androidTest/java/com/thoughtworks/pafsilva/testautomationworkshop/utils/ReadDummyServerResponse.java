package com.thoughtworks.pafsilva.testautomationworkshop.utils;

import android.support.test.InstrumentationRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ReadDummyServerResponse {

    public static String readJsonFile(String fileName) throws IOException {
        InputStream jsonFixtureFile = InstrumentationRegistry.getContext().getAssets().open("files/" + fileName);
        Scanner s = new Scanner(jsonFixtureFile).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }
}