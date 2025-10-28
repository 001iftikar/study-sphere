package com.iftikar.studysphere.shared

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object LocalUserSessionSerializer : Serializer<LocalUserSession> {
    override val defaultValue: LocalUserSession
        get() = LocalUserSession()

    override suspend fun readFrom(input: InputStream): LocalUserSession {
        return try {
            Json.decodeFromString(
                deserializer = LocalUserSession.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (ex: SerializationException) {
            ex.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: LocalUserSession,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = LocalUserSession.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}