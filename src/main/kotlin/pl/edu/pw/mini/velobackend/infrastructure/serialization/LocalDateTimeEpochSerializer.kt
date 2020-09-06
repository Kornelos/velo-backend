package pl.edu.pw.mini.velobackend.infrastructure.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class LocalDateTimeEpochSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTimeSerializer", PrimitiveKind.LONG)
    private var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeLong(value.toEpochSecond(ZoneOffset.UTC))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.ofEpochSecond(decoder.decodeLong(), 0, ZoneOffset.UTC)
}