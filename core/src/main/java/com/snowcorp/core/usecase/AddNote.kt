package com.snowcorp.core.usecase

import com.snowcorp.core.data.Note
import com.snowcorp.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}