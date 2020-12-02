package pl.edu.pw.mini.velobackend.domain.metrics

import pl.edu.pw.mini.velobackend.domain.workout.DataSeries
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.pow

class MetricsFactory {
    companion object {
        fun createMetricsFor(workout: Workout): Metrics = createMetricsFor(workout.dataSeries)

        fun createMetricsFor(data: DataSeries): Metrics {

            val totalTime = Duration.ofSeconds(data.time.last().toLong())
            val totalWork = data.power.sum().toFloat() / 1000 // kiloJoules
            val totalCalories = totalWork / 4.184
            val altitudePairwiseDiff = data.altitude.windowed(2, 1).map { it[1] - it[0] }
            val totalDescent = altitudePairwiseDiff.filter { it < 0 }.sum().absoluteValue
            val totalAscent = altitudePairwiseDiff.filter { it > 0 }.sum()
            val powerCurve = PowerCurveCalculator.calculate(data.power, data.time.last())

            return Metrics(
                    totalElapsedTime = totalTime,
                    totalMovingTime = totalTime,
                    totalDistance = data.distance.last(),
                    totalWork = totalWork,
                    totalCalories = totalCalories.toFloat(),
                    avgSpeed = data.velocity.average().toFloat(),
                    maxSpeed = data.velocity.maxOrNull(),
                    avgPower = data.power.average().toFloat(),
                    maxPower = data.power.maxOrNull(),
                    totalAscent = totalAscent,
                    totalDescent = totalDescent,
                    normalizedPower = calculateNormalizedPower(data.power).toInt(),
                    avgHeartRate = data.heartrate.average().toFloat(),
                    maxHeartRate = data.heartrate.maxOrNull(),
                    maxCadence = data.cadence.maxOrNull(),
                    avgCadence = data.cadence.average().toFloat(),
                    powerCurve = powerCurve
            )
        }

        private fun calculateNormalizedPower(powers: List<Int>) =
                powers.windowed(30, 1).map { it.average().pow(4) }.average().pow(0.25)
    }
}