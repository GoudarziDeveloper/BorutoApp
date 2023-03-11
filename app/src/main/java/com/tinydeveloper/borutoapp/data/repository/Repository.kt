package com.tinydeveloper.borutoapp.data.repository

import androidx.paging.PagingData
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.domain.repository.DataStoreOperations
import com.tinydeveloper.borutoapp.domain.repository.LocalDataSource
import com.tinydeveloper.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource
    ) {

    fun getAllHeroes(): Flow<PagingData<Hero>>{
        return remote.getAllHeroes()
    }

    suspend fun getSelectedHero(heroId: Int): Hero{
        return localDataSource.getSelectedHero(heroId = heroId)
    }

    suspend fun saveOnBoardingState(completed: Boolean){
        dataStore.saveOnBoardingState(completed)
    }

    fun readOnBoardingState(): Flow<Boolean>{
        return dataStore.readOnBoardingState()
    }

    fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return remote.searchHeroes(query = query)
    }
}