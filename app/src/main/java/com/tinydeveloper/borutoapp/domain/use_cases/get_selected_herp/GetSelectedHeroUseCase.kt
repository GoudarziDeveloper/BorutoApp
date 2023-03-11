package com.tinydeveloper.borutoapp.domain.use_cases.get_selected_herp

import com.tinydeveloper.borutoapp.data.repository.Repository
import com.tinydeveloper.borutoapp.domain.model.Hero

class GetSelectedHeroUseCase(private val repository: Repository) {
    suspend operator fun invoke(heroId: Int): Hero{
        return repository.getSelectedHero(heroId = heroId)
    }
}