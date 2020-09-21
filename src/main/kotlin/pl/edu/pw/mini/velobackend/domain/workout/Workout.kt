package pl.edu.pw.mini.velobackend.domain.workout

import pl.edu.pw.mini.velobackend.domain.metrics.Metrics
import pl.edu.pw.mini.velobackend.domain.model.Location
import java.time.LocalDateTime
import java.util.UUID

data class Workout(
        val id: UUID = UUID.randomUUID(),
        //TODO: laps
        val name: String = "Unnamed Workout",
        val type: WorkoutType,
        val athleteId: UUID,
        val dataSeries: DataSeries,
        val metrics: Metrics?,
        val stravaId: Long?,
        val startDateTime: LocalDateTime
)

data class DataSeries(
        val time: List<Int> = emptyList(),
        val distance: List<Float> = emptyList(),
        val latlng: List<Location> = emptyList(),
        val altitude: List<Float> = emptyList(),
        val velocity: List<Float> = emptyList(),
        val heartrate: List<Int> = emptyList(),
        val cadence: List<Int> = emptyList(),
        val power: List<Int> = emptyList(),
)