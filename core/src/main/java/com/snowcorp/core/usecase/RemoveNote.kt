package com.snowcorp.core.usecase

import com.snowcorp.core.data.Note
import com.snowcorp.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}