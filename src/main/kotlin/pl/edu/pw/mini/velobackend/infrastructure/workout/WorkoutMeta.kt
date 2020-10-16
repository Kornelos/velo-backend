package pl.edu.pw.mini.velobackend.infrastructure.workout

import kotlinx.serialization.Serializable
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.infrastructure.serialization.DurationSerializer
import pl.edu.pw.mini.velobackend.infrastructure.serialization.LocalDateTimeEpochSerializer
import pl.edu.pw.mini.velobackend.infrastructure.serialization.UUIDSerializer
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class WorkoutMeta(
        val name: String,
        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
        @Serializable(with = UUIDSerializer::class)
        val athleteId: UUID,
        @Serializable(with = LocalDateTimeEpochSerializer::class)
        val startDateTime: LocalDateTime,
        @Serializable(with = DurationSerializer::class)
        val totalTime: Duration,
        val totalDistance: Float
)

fun Workout.toWorkoutMeta() = WorkoutMeta(
        name = name,
        id = id,
        athleteId = athleteId,
        startDateTime = startDateTime,
        totalTime = Duration.ofSeconds(dataSeries.time.lastOrNull()?.toLong() ?: 0),
        totalDistance = dataSeries.distance.lastOrNull() ?: 0.0F
)
