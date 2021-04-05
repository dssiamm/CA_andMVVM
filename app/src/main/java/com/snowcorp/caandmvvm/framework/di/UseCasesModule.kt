package com.snowcorp.caandmvvm.framework.di

import com.snowcorp.caandmvvm.framework.UseCases
import com.snowcorp.core.repository.NoteRepository
import com.snowcorp.core.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun provideUseCases(repository: NoteRepository) = UseCases(
            AddNote(repository),
            GetAllNotes(repository),
            GetNote(repository),
            RemoveNote(repository),
            GetWordCount()
    )
}