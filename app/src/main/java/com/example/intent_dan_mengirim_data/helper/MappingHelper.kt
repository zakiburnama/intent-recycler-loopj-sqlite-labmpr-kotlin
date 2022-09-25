package com.example.intent_dan_mengirim_data.helper

import android.database.Cursor
import com.example.intent_dan_mengirim_data.Homework
import com.example.intent_dan_mengirim_data.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Homework> {
        val notesList = ArrayList<Homework>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                notesList.add(Homework(id, title, description, date))
            }
        }
        return notesList
    }
}