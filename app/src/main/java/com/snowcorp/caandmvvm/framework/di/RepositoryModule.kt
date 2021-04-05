package com.snowcorp.caandmvvm.framework.di

import android.app.Application
import com.snowcorp.caandmvvm.framework.RoomNoteDataSource
import com.snowcorp.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}