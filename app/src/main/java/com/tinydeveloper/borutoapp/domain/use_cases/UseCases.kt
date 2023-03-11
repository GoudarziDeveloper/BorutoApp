package com.tinydeveloper.borutoapp.domain.use_cases

import com.tinydeveloper.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.get_selected_herp.GetSelectedHeroUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase

class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroesUseCase: SearchHeroesUseCase,
    val getSelectedHeroUseCase: GetSelectedHeroUseCase
)