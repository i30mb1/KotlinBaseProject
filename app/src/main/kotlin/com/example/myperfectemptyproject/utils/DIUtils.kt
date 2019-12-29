@file:Suppress("UNCHECKED_CAST")

package com.example.myperfectemptyproject.utils

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.example.myperfectemptyproject.ui.main.ViewModelAssistedFactory

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline provider: () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.viewModelWithSavedStateHandle(
    defaultArgs: Bundle? = null,
    crossinline provider: () -> ViewModelAssistedFactory<T>
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, defaultArgs) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return provider().create(handle) as T
        }
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(
    crossinline provider: () -> T
) = activityViewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.navGraphViewModel(
    id: Int,
    crossinline provider: () -> T
) = navGraphViewModels<T>(id) {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}
