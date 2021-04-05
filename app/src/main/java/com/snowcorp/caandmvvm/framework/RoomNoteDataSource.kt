package com.snowcorp.caandmvvm.framework

import android.content.Context
import com.snowcorp.core.data.Note
import com.snowcorp.caandmvvm.framework.db.DatabaseService
import com.snowcorp.caandmvvm.framework.db.NoteEntity
import com.snowcorp.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).getDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}