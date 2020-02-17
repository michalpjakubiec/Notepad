package com.example.notepad.note.utils

import com.example.notepad.db.models.Note

sealed class NoteLoadSaveResult {
    object NotStarted : NoteLoadSaveResult()
    object Pending : NoteLoadSaveResult()
    data class Completed(val result: Note?) : NoteLoadSaveResult()
    data class Failed(val error: String?) : NoteLoadSaveResult()
}