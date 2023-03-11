package com.tinydeveloper.borutoapp.presentaion.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.domain.use_cases.UseCases
import com.tinydeveloper.borutoapp.util.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    useCase: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _selectedHero: MutableStateFlow<Hero?> = MutableStateFlow(null)
    val selectedHero: StateFlow<Hero?> = _selectedHero
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedHero.value = heroId?.let { useCase.getSelectedHeroUseCase(heroId = it) }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun generateColorPallet(){
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPallet)
        }
    }

    fun setColorPalette(colorPalette: Map<String, String>){
        _colorPalette.value = colorPalette
    }
}

sealed class UiEvent(){
    object GenerateColorPallet: UiEvent()
}