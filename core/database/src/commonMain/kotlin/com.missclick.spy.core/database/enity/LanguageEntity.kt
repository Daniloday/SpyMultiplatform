package com.missclick.spy.core.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.missclick.spy.core.model.Language

@Entity(tableName = "language")
internal data class LanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "name")
    val name: String,
)

internal fun LanguageEntity.asModel(): Language {
    return Language(
        name = name,
        code = code
    )
}