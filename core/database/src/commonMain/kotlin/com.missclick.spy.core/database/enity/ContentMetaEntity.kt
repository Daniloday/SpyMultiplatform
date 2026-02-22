package com.missclick.spy.core.database.enity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_meta")
internal data class ContentMetaEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 1,

    @ColumnInfo(name = "schema")
    val schema: String,

    @ColumnInfo(name = "content_version")
    val contentVersion: Int,

    @ColumnInfo(name = "generated_at")
    val generatedAt: String? = null,

    @ColumnInfo(name = "updated_at_epoch_ms")
    val updatedAtEpochMs: Long,
)