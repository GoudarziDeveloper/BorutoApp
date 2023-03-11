package com.tinydeveloper.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tinydeveloper.borutoapp.util.HERO_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val militaryRank: String,
    val popularity : Double,
    val highlights: String,
    val ageOf: String,
    val year: String,
    val family: List<String>,
    val positives: List<String>,
    val negatives: List<String>
)
