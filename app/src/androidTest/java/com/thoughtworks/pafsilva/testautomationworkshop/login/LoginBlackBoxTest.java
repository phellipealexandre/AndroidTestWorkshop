package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.content.Context;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LoginBlackBoxTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();

        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000);

        Context context = InstrumentationRegistry.getContext();
        String activityPackage = "com.thoughtworks.pafsilva.testautomationworkshop";
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(activityPackage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        device.wait(Until.hasObject(By.pkg(activityPackage).depth(0)), 5000);
    }

    @Test
    public void shouldShowTitleWhenOpenActivity() throws Exception {
        UiObject2 result = device.findObject(By.clazz("android.widget.Button"));
        assertEquals("DO LOGIN", result.getText());
    }
}
