package com.missclick.spy.core.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.missclick.spy.core.model.Word

@Entity(
    tableName = "word",
    foreignKeys = [
        ForeignKey(
            entity = SetEntity::class,
            parentColumns = ["id"],
            childColumns = ["set_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["set_id"]),
    ]
)
internal data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "set_id")
    val setId: Int,
    @ColumnInfo(name = "is_hidden")
    val isHidden: Boolean = false,
)

internal fun Word.asEntity(collectionId: Int): WordEntity {
    return WordEntity(
        name = wordName,
        setId = collectionId,
        isHidden = isHidden,
    )
}







