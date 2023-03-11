package com.tinydeveloper.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tinydeveloper.borutoapp.data.local.BorutoDatabase
import com.tinydeveloper.borutoapp.data.remote.BorutoApi
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.domain.model.HeroRemoteKeys
import com.tinydeveloper.borutoapp.util.REFRESH_MINUTES_TIME
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) :RemoteMediator<Int, Hero>() {
    private val heroDao = borutoDatabase.heroDao()
    private val remoteKeysDao = borutoDatabase.heroRemoteKeys()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = remoteKeysDao.getRemoteKey(heroId = 1)?.lastUpdated ?: 0L
        val diffInMinutes = (currentTime - lastUpdate) / 1000 / 60
        return if (diffInMinutes <= REFRESH_MINUTES_TIME){
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        return try {
            val page = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage?:
                        return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage?:
                        return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }
            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()){
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH){
                        heroDao.deleteAllHeroes()
                        remoteKeysDao.deleteAllRemoteKeys()
                    }

                    val keys = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = response.prevPage,
                            nextPage = response.nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroDao.addHeroes(heroes = response.heroes)
                    remoteKeysDao.addAllRemoteKes(remoteKeys = keys)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (error: Exception){
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKey(heroId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { hero -> remoteKeysDao.getRemoteKey(heroId = hero.id) }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { hero -> remoteKeysDao.getRemoteKey(heroId = hero.id) }
    }
/*
    private fun parseMillis(millis: Long): String{
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }*/
}