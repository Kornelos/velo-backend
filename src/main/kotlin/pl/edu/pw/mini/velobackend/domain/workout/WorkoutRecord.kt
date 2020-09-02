package pl.edu.pw.mini.velobackend.domain.workout

import pl.edu.pw.mini.velobackend.domain.model.Location
import java.time.LocalDateTime

data class WorkoutRecord(
        val location: Location?,
        val timestamp: LocalDateTime,
        val distance: Double?,
        val altitude: Double?,
        val speed: Double?,
        val heartRate: Int?,
        val cadence: Int?,
        val power: Int?
)