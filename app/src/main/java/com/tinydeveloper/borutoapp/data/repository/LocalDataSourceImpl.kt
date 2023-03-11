package com.tinydeveloper.borutoapp.data.repository

import com.tinydeveloper.borutoapp.data.local.BorutoDatabase
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(borutoDatabase: BorutoDatabase): LocalDataSource {
    private val heroDao = borutoDatabase.heroDao()
    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}