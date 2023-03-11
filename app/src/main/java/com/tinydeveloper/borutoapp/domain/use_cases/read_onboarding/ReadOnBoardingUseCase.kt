package com.tinydeveloper.borutoapp.domain.use_cases.read_onboarding

import com.tinydeveloper.borutoapp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(val repository: Repository) {
    operator fun invoke(): Flow<Boolean>{
        return repository.readOnBoardingState()
    }
}