package pl.edu.pw.mini.velobackend.domain.athlete

import java.time.LocalDate
import java.util.*

data class Athlete(
        val id: UUID,
        val name: String,
        val age: Int,
        val maxHeartRate: Int,
        val gender: String,
        val height: Int,
        val weights: Map<LocalDate, Int>,
        val ftps: Map<LocalDate, Int>,
        val thresholdHeartRates: Map<LocalDate, Int>
)