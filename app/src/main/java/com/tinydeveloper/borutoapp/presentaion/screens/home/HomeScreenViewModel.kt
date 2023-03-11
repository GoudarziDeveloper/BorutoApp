package com.tinydeveloper.borutoapp.presentaion.screens.home

import androidx.lifecycle.ViewModel
import com.tinydeveloper.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(useCases: UseCases): ViewModel() {
    val getAllHeroes = useCases.getAllHeroesUseCase()
}