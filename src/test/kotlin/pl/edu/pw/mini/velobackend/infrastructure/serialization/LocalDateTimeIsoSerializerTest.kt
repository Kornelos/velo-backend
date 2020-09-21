package pl.edu.pw.mini.velobackend.infrastructure.serialization

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeIsoSerializerTest {

    @Test
    fun `should serialize and deserialize LocalDateTime ISO format`() {
        //given
        val dateTimeString = "2020-09-13T08:31:08Z"
        val dateTime = LocalDateTime.of(2020, 9, 13, 8, 31, 8)

        //when
        //then
        LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME) `should be equal to` dateTime
    }
}