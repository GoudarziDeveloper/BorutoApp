package com.tinydeveloper.borutoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tinydeveloper.borutoapp.domain.model.HeroRemoteKeys
import com.tinydeveloper.borutoapp.util.HERO_REMOTE_KEYS_DATABASE_TABLE

@Dao
interface HeroRemoteKeysDao {
    @Query("SELECT * FROM $HERO_REMOTE_KEYS_DATABASE_TABLE WHERE id=:heroId")
    suspend fun getRemoteKey(heroId: Int): HeroRemoteKeys?

    @Insert(onConflict = REPLACE)
    suspend fun addAllRemoteKes(remoteKeys: List<HeroRemoteKeys>)

    @Query("DELETE FROM $HERO_REMOTE_KEYS_DATABASE_TABLE")
    suspend fun deleteAllRemoteKeys()
}
