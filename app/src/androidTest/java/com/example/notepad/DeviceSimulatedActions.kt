package com.example.notepad

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.example.notepad.main.ui.MainActivity
import com.example.notepad.utils.Utils
import com.example.notepad.utils.Utils.isNetworkEnabled
import com.example.notepad.utils.Utils.setNetwork
import com.example.notepad.utils.customRules.ScreenshotTestRule
import com.example.notepad.utils.degree_0
import com.example.notepad.utils.degree_270
import com.example.notepad.utils.degree_90
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)

class DeviceSimulatedActions {

    private val timeout = 1000L
    private val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val mAppContext =
        InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule val mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule val screenshotTestRule = ScreenshotTestRule()


    @Test
    fun goToSleepAndWakeUp() {
        assertThat(mDevice.isScreenOn, equalTo(true))

        mDevice.sleep()
        Thread.sleep(timeout)
        assertThat(mDevice.isScreenOn, equalTo(false))

        mDevice.wakeUp()
        Thread.sleep(timeout)
        assertThat(mDevice.isScreenOn, equalTo(true))
    }

    @Test
    fun rotateScreen() {
        mDevice.setOrientationLeft()
        Thread.sleep(timeout)
        assertThat(mDevice.displayRotation, equalTo(degree_90))

        mDevice.setOrientationRight()
        Thread.sleep(timeout)
        assertThat(mDevice.displayRotation, equalTo(degree_270))

        mDevice.setOrientationNatural()
        Thread.sleep(timeout)
        assertThat(mDevice.displayRotation, equalTo(degree_0))
    }

    @Test
    fun minimizeAndRestore() {
        mDevice.pressHome()
        Thread.sleep(timeout)
        mDevice.pressRecentApps()
        Thread.sleep(timeout)
        mDevice.pressRecentApps()
        Thread.sleep(timeout)

        // TODO: add assertion for current view
    }

    @Test
    fun manageNetwork() {
        setNetwork(Utils.NetworkType.WIFI, false)
        Thread.sleep(timeout)
        assertThat(isNetworkEnabled(Utils.NetworkType.WIFI, mAppContext), equalTo(false));

        setNetwork(Utils.NetworkType.CELLULAR, false)
        Thread.sleep(timeout)
        assertThat(isNetworkEnabled(Utils.NetworkType.CELLULAR, mAppContext), equalTo(false));
    }
}
