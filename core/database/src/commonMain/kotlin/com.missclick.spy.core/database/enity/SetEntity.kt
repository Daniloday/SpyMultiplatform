package com.missclick.spy.core.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.missclick.spy.core.model.Set
import kotlin.time.Clock

@Entity(
    tableName = "set",
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["code"],
            childColumns = ["language_code"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["language_code"]),
        Index(value = ["language_code", "key"], unique = true)
    ]
)
internal data class SetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "key")
    val key: String, // city_life, entertainment, etc

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "language_code")
    val languageCode: String,

    @ColumnInfo(name = "is_custom")
    val isCustom: Boolean = false,

    @ColumnInfo(name = "is_premium")
    val isPremium: Boolean = false,

    @ColumnInfo(name = "is_pro")
    val isPro: Boolean = false,
)

internal fun SetEntity.asModel(): Set {
    return Set(
        name = name,
        isCustom = isCustom,
        isPremium = isPremium,
        isPro = isPro,
        key = key
    )
}

internal fun Set.asEntity(languageCode: String): SetEntity {

    val slug = name
        .lowercase()
        .replace(Regex("[^a-z0-9]+"), "_")
        .trim('_')

    return SetEntity(
        name = name,
        key = "custom_${slug}_${Clock.System.now().toEpochMilliseconds()}",
        languageCode = languageCode,
        isCustom = isCustom,
        isPro = isPro,
        isPremium = isPremium,
    )
}