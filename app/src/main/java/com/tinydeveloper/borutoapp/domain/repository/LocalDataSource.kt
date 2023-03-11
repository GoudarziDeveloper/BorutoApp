package com.tinydeveloper.borutoapp.domain.repository

import com.tinydeveloper.borutoapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}