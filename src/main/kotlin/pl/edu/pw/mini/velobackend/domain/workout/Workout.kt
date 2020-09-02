package pl.edu.pw.mini.velobackend.domain.workout

import pl.edu.pw.mini.velobackend.domain.metrics.Metrics
import java.util.*

data class Workout(
        val id: UUID,
        val workoutRecords: List<WorkoutRecord>,
        val lapRecords: List<LapRecord>,
        val type: WorkoutType,
        val metrics: Metrics?
)