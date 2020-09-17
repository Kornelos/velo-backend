package pl.edu.pw.mini.velobackend.domain.workout

import pl.edu.pw.mini.velobackend.domain.metrics.Metrics
import java.util.UUID

data class Workout(
        val id: UUID = UUID.randomUUID(),
        val workoutRecords: List<WorkoutRecord>,
        val lapRecords: List<LapRecord>,
        val type: WorkoutType,
        // val athleteId: UUID,
        val metrics: Metrics?
)