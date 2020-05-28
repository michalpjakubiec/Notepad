package com.example.notepad

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    NoteFragmentUITest::class
//    DeviceSimulatedActions::class
)
class MainActivityTestSuite