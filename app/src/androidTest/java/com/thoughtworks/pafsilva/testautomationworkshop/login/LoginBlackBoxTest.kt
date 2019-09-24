package com.thoughtworks.pafsilva.testautomationworkshop.login

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.core.IsNull.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginBlackBoxTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000)

        val context = InstrumentationRegistry.getContext()
        val activityPackage = "com.thoughtworks.pafsilva.testautomationworkshop"
        val intent = context.packageManager.getLaunchIntentForPackage(activityPackage)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg(activityPackage).depth(0)), 5000)
    }

    @Test
    @Throws(Exception::class)
    fun shouldShowTitleWhenOpenActivity() {
        val result = device.findObject(By.clazz("android.widget.Button"))
        assertEquals("DO LOGIN", result.text)
    }
}
