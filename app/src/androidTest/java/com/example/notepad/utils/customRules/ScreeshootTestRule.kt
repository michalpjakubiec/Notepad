package com.example.notepad.utils.customRules

import android.graphics.Bitmap
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.ScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomScreenCaptureProcessor : BasicScreenCaptureProcessor() {
    init {
        mTag = "CustomScreenCaptureProcessor"
        mDefaultFilenamePrefix = ""
        mDefaultScreenshotPath = getNewFilename()
    }

    private fun getNewFilename(): File? {
        val context = getInstrumentation().targetContext.applicationContext
        return context.getExternalFilesDir(DIRECTORY_PICTURES)
    }

    // TODO: store images in sdcard instead of app folder because of deleting
    //  that folder when finishing connectedAndroidTest

    override fun getFilename(prefix: String): String? = prefix
}

class ScreenshotTestRule : TestWatcher() {
    override fun failed(e: Throwable, description: Description?) {
        super.finished(description)

        val className = description?.testClass?.simpleName ?: "NullClassname"
        val methodName = description?.methodName ?: "NullMethodName"
        val androidRuntimeVersion = Build.VERSION.SDK_INT
        val androidDeviceName = Build.DEVICE

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val sb = StringBuilder()
        val separator = "_"

        sb.append("API")
        sb.append(androidRuntimeVersion)
        sb.append(separator)
        sb.append(androidDeviceName)
        sb.append(separator)
        sb.append(className)
        sb.append(separator)
        sb.append(methodName)
        sb.append(separator)
        sb.append(formattedDateTime)
        sb.append(separator)

        val filename = sb.toString()

        val capture = Screenshot.capture()
        capture.name = filename
        capture.format = Bitmap.CompressFormat.PNG

        val processors = HashSet<ScreenCaptureProcessor>()
        processors.add(CustomScreenCaptureProcessor())

        try {
            capture.process(processors)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}