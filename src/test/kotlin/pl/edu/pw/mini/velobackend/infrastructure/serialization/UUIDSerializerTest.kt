package pl.edu.pw.mini.velobackend.infrastructure.serialization

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.UUID

class UUIDSerializerTest {

    @Test
    fun `should serialize and deserialize UUID`() {
        //given
        val uuidString = "285044cf-b438-4f88-ae68-b995f300b2d9"

        //when
        val uuid = UUID.fromString(uuidString)

        //then
        uuid.toString() `should be equal to` uuidString
    }
}