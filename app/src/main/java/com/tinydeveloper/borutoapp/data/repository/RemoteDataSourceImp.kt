package com.tinydeveloper.borutoapp.data.repository

import androidx.paging.*
import com.tinydeveloper.borutoapp.data.local.BorutoDatabase
import com.tinydeveloper.borutoapp.data.paging_source.HeroRemoteMediator
import com.tinydeveloper.borutoapp.data.paging_source.SearchHeroesSource
import com.tinydeveloper.borutoapp.data.remote.BorutoApi
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.domain.repository.RemoteDataSource
import com.tinydeveloper.borutoapp.util.SIZE_PER_PAGE
import kotlinx.coroutines.flow.Flow

class  RemoteDataSourceImp(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
): RemoteDataSource {
    private val heroDao = borutoDatabase.heroDao()
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = SIZE_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = SIZE_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSource(borutoApi = borutoApi, query = query)
            }
        ).flow
    }
}