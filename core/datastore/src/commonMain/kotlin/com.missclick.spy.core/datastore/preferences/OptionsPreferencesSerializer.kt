package com.missclick.spy.core.datastore.preferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import com.missclick.spy.core.common.Constant.PLAYERS_DEFAULT
import com.missclick.spy.core.common.Constant.SPIES_DEFAULT
import com.missclick.spy.core.common.Constant.TIMER_DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal class OptionsPreferencesSerializer(
    override val defaultValue: OptionsPreferences = OptionsPreferences(
        playersCount = PLAYERS_DEFAULT,
        spiesCount = SPIES_DEFAULT,
        time = TIMER_DEFAULT,
        collectionName = "",
        selectedLanguageCode = "",
        collectionLanguageCode = "",
    )
) : OkioSerializer<OptionsPreferences> {

    override suspend fun readFrom(source: BufferedSource): OptionsPreferences {
        return try {
            Json.decodeFromString(
                OptionsPreferences.serializer(), source.readUtf8()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }
    }

    override suspend fun writeTo(t: OptionsPreferences, sink: BufferedSink) {
        withContext(Dispatchers.IO) {
            sink.write(
                Json.encodeToString(OptionsPreferences.serializer(), t)
                    .encodeToByteArray()
            )
        }
    }
}