package pl.edu.pw.mini.velobackend.domain.metrics

import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRecord
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.pow

class MetricsFactory {
    companion object {
        fun createMetricsFrom(workoutRecords: Collection<WorkoutRecord>): Metrics {
            val totalTime = Duration.between(workoutRecords.first().timestamp, workoutRecords.last().timestamp)
            val powers = workoutRecords.filterNot { it.power == null }.map { it.power!! }
            val speeds = workoutRecords.filterNot { it.speed == null }.map { it.speed!! }
            val heartRates = workoutRecords.filterNot { it.heartRate == null }.map { it.heartRate!! }
            val cadences = workoutRecords.filterNot { it.cadence == null }.map { it.cadence!! }
            val totalWork = powers.sum() / 1000 // kiloJoules
            val totalCalories = totalWork / 4.184
            val altitudePairwiseDiff = workoutRecords.filterNot { it.altitude == null }.map { it.altitude!! }
                    .windowed(2, 1).map { it[1] - it[0] }
            val totalDescent = altitudePairwiseDiff.filter { it < 0 }.sum().absoluteValue
            val totalAscent = altitudePairwiseDiff.filter { it > 0 }.sum()

            return Metrics(
                    totalElapsedTime = totalTime,
                    totalMovingTime = totalTime,
                    totalDistance = workoutRecords.last().distance,
                    totalWork = totalWork,
                    totalCalories = totalCalories.toInt(),
                    avgSpeed = speeds.average(),
                    maxSpeed = speeds.maxOrNull(),
                    avgPower = powers.average().toInt(),
                    maxPower = powers.maxOrNull(),
                    totalAscent = totalAscent.toInt(),
                    totalDescent = totalDescent.toInt(),
                    normalizedPower = calculateNormalizedPower(powers).toInt(),
                    avgHeartRate = heartRates.average().toInt(),
                    maxHeartRate = heartRates.maxOrNull(),
                    maxCadence = cadences.maxOrNull(),
                    avgCadence = cadences.average().toInt()
            )
        }

        private fun calculateNormalizedPower(powers: List<Int>) =
                powers.windowed(30, 1).map { it.average().pow(4) }.average().pow(0.25)
    }
}