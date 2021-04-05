package com.snowcorp.caandmvvm.framework.di

import com.snowcorp.caandmvvm.framework.ListViewModel
import com.snowcorp.caandmvvm.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}