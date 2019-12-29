package com.example.myperfectemptyproject.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.myperfectemptyproject.data.source.remote.ApiErrorHandle
import com.example.myperfectemptyproject.di.ViewModelAssistedFactory
import com.example.myperfectemptyproject.ui.main.domain.usecase.UseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    application: Application,
    @Assisted private val handle: SavedStateHandle,
    private val useCase: UseCase
) : AndroidViewModel(application) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<MainViewModel>

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        viewModelScope.launch {
            useCase.execute()
                .onSuccess {

                }
                .onFailure {
                    _errorMessage.value = ApiErrorHandle.traceErrorException(it).getErrorMessage()
                    _errorMessage.value = null
                }
        }
    }

}
