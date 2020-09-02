package pl.edu.pw.mini.velobackend.domain.workout

import pl.edu.pw.mini.velobackend.domain.model.Location
import java.time.LocalDateTime

data class LapRecord(
        val timestamp: LocalDateTime,
        val startTime: LocalDateTime,
        val startLocation: Location,
        val endLocation: Location
)
