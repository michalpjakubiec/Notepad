package com.example.notepad.db.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: String?,
    val created: Date?,
    var title: String?,
    var content: String?
) : Parcelable