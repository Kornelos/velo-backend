package pl.edu.pw.mini.velobackend.infrastructure.workout

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import pl.edu.pw.mini.velobackend.domain.metrics.Metrics
import pl.edu.pw.mini.velobackend.domain.workout.DataSeries
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutType
import java.time.LocalDateTime
import java.util.UUID

data class WorkoutDto(
        @Id val id: UUID,
        val name: String,
        val type: WorkoutType,
        @Indexed val athleteId: UUID,
        val dataSeries: DataSeries,
        val metrics: Metrics?,
        val stravaId: Long?,
        val startDateTime: LocalDateTime
) {
    fun toWorkout() = Workout(id, name, type, athleteId, dataSeries, metrics, stravaId, startDateTime)
}

fun Workout.toWorkoutDto() = WorkoutDto(id, name, type, athleteId, dataSeries, metrics, stravaId, startDateTime)