package com.tinydeveloper.borutoapp.domain.use_cases.save_onboarding

import com.tinydeveloper.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(val repository: Repository) {
    suspend operator fun invoke(completed: Boolean){
        repository.saveOnBoardingState(completed)
    }
}