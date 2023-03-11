package com.tinydeveloper.borutoapp.presentaion.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinydeveloper.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _readOnBoardingState = MutableStateFlow(false)
    val readOnBoardingState: StateFlow<Boolean> = _readOnBoardingState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _readOnBoardingState.value =
                useCases.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }
}