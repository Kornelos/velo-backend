package pl.edu.pw.mini.velobackend.infrastructure.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeIsoSerializer : KSerializer<LocalDateTime?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTimeSerializer", PrimitiveKind.STRING)
    private var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: LocalDateTime?) {
        if (value != null) {
            encoder.encodeString(value.format(dateTimeFormatter))
        } else {
            encoder.encodeNull()
        }
    }

    override fun deserialize(decoder: Decoder): LocalDateTime? {
        return try {
            LocalDateTime.parse(decoder.decodeString(), dateTimeFormatter)
        } catch (ignored: Exception) {
            null
        }
    }
}