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
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["set_id"])
    ]
)
internal data class WordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "set_id")
    val setId: Long,
)

internal fun Word.asEntity(collectionId: Long): WordEntity {
    return WordEntity(
        text = wordName,
        setId = collectionId,
    )
}







