package com.example.notepad

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    AddNoteTest::class
//    DeviceSimulatedActions::class
)
class ActivityTestSuite