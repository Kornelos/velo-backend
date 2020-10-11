package pl.edu.pw.mini.velobackend.infrastructure.athlete

import org.springframework.data.annotation.Id
import pl.edu.pw.mini.velobackend.domain.athlete.Athlete
import java.time.LocalDate
import java.util.UUID

data class AthleteDto(
        @Id val id: UUID,
        val firstName: String?,
        val lastName: String?,
        val age: Int?,
        val maxHeartRate: Int?,
        val gender: String?,
        val height: Int?,
        val weights: Map<LocalDate, Int>,
        val ftps: Map<LocalDate, Int>,
        val thresholdHeartRates: Map<LocalDate, Int>
) {
    fun toAthlete() = Athlete(id, firstName, lastName, age, maxHeartRate, gender, height, weights, ftps, thresholdHeartRates)
}

fun Athlete.toAthleteDto() = AthleteDto(id, firstName, lastName, age, maxHeartRate, gender, height, weights, ftps, thresholdHeartRates)