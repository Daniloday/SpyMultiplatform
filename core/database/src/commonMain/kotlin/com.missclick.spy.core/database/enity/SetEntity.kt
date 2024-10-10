package com.missclick.spy.core.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.missclick.spy.core.model.Set

@Entity(
    tableName = "set",
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["language_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["language_id"]),
    ]
)
internal data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "language_id")
    val languageId: Int,
    @ColumnInfo(name = "is_custom")
    val isCustom: Boolean = true,
)

internal fun SetEntity.asModel(): Set {
    return Set(
        name = name,
        isCustom = isCustom,
    )
}

internal fun Set.asEntity(languageId: Int): SetEntity {
    return SetEntity(
        name = name,
        languageId = languageId,
        isCustom = isCustom
    )
}