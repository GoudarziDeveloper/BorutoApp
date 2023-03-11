package com.tinydeveloper.borutoapp.di

import android.content.Context
import com.tinydeveloper.borutoapp.data.repository.DataStoreOperationImpl
import com.tinydeveloper.borutoapp.domain.repository.DataStoreOperations
import com.tinydeveloper.borutoapp.data.repository.Repository
import com.tinydeveloper.borutoapp.domain.use_cases.UseCases
import com.tinydeveloper.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.get_selected_herp.GetSelectedHeroUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.tinydeveloper.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDataStoreOperation(@ApplicationContext context: Context): DataStoreOperations{
        return DataStoreOperationImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases{
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }
}