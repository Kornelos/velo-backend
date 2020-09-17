package pl.edu.pw.mini.velobackend.infrastructure.strava

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.AuthorizationResponse
import java.time.LocalDateTime

class AuthorizationResponseTest {

    @Test
    fun `should serialize and deserialize JSON`() {

        //given
        val jsonString = """
     {"token_type":"Bearer","expires_at":1599352732,"expires_in":20064,"refresh_token":"token_example",
     "access_token":"token_example","athlete":{"id":628749,"username":"kskrka","resource_state":2,
     "firstname":"Kornel","lastname":"Skórka","city":"Zamosc","state":"Lublin Voivodeship","country":"Poland",
     "sex":"M","premium":false,"summit":false,"created_at":"2012-06-16T14:55:39Z","updated_at":"2020-06-10T10:34:58Z",
     "badge_type_id":0,"profile_medium":"https://dgalywyr863hv.cloudfront.net/pictures/athletes/628749/595104/6/medium.jpg",
     "profile":"https://dgalywyr863hv.cloudfront.net/pictures/athletes/628749/595104/6/large.jpg","friend":null,"follower":null}}
     """.trimIndent().replace("\n", "")

        //when
        val authorizationResponse = Json.decodeFromString<AuthorizationResponse>(jsonString)

        //then
        authorizationResponse.tokenType `should be equal to` "Bearer"
        authorizationResponse.athlete.firstName `should be equal to` "Kornel"
        authorizationResponse.expiresIn.toSeconds() `should be equal to` 20064
        authorizationResponse.expiresAt `should be equal to` LocalDateTime.of(2020, 9, 6, 0, 38, 52)

        //when
        val jsonStringFromObject = Json.encodeToString(authorizationResponse)

        //then
        jsonStringFromObject `should be equal to` jsonString
    }
}