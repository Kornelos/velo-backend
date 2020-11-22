package pl.edu.pw.mini.velobackend.infrastructure.workout

import pl.edu.pw.mini.velobackend.domain.metrics.Metrics
import pl.edu.pw.mini.velobackend.domain.model.Location
import pl.edu.pw.mini.velobackend.domain.workout.DataSeries
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutType
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.ActivityType
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.SummaryActivity
import pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams.StreamSet
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

object WorkoutMapper {
    fun createWorkout(activity: SummaryActivity, streamSet: StreamSet, athleteId: UUID): Workout {
        return Workout(
                name = activity.name ?: "Unnamed Workout",
                type = parseActivityType(activity.type),
                stravaId = activity.id,
                athleteId = athleteId,
                startDateTime = activity.startDate ?: LocalDateTime.MIN,
                dataSeries = DataSeries(
                        streamSet.time?.data ?: emptyList(),
                        streamSet.distance?.data ?: emptyList(),
                        streamSet.latlng?.data?.map { Location(it[0], it[1]) } ?: emptyList(),
                        streamSet.altitude?.data ?: emptyList(),
                        streamSet.velocitySmooth?.data ?: emptyList(),
                        streamSet.heartrate?.data ?: emptyList(),
                        streamSet.cadence?.data ?: emptyList(),
                        streamSet.watts?.data?.map { it ?: 0 } ?: emptyList()
                ),
                metrics = Metrics.Builder()
                        .totalElapsedTime(activity.elapsedTime?.toLong()?.let { Duration.ofSeconds(it) })
                        .totalMovingTime(activity.elapsedTime?.toLong()?.let { Duration.ofSeconds(it) })
                        .totalDistance(activity.distance)
                        .totalWork(activity.kilojoules)
                        .avgSpeed(activity.averageSpeed)
                        .avgPower(activity.averageWatts)
                        .maxPower(activity.maxWatts)
                        .normalizedPower(activity.weightedAverageWatts)
                        .build()
        )
    }

    private fun parseActivityType(stravaType: ActivityType?): WorkoutType {
        return when (stravaType) {
            ActivityType.Ride -> WorkoutType.Bike
            ActivityType.VirtualRide -> WorkoutType.IndoorBike
            ActivityType.Run -> WorkoutType.Run
            else -> WorkoutType.Other
        }
    }
}