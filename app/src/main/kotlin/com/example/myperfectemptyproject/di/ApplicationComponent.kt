package com.example.myperfectemptyproject.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myperfectemptyproject.ui.main.MainViewModel
import com.squareup.inject.assisted.AssistedInject
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitModule::class, RepositoryModule::class, AssistedInjectModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Application): ApplicationComponent
    }

//    val mainViewModel: MainViewModel

    val mainViewModelFactory: MainViewModel.Factory
}

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
abstract class AssistedInjectModule

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}