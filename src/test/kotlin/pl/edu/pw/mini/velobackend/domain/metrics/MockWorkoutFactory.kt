package pl.edu.pw.mini.velobackend.domain.metrics

import pl.edu.pw.mini.velobackend.domain.model.Location
import pl.edu.pw.mini.velobackend.domain.workout.DataSeries
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutType
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

class MockWorkoutFactory {
    fun createMockTrainingWithMetrics(dataPoints: Int): Workout {
        val ds = DataSeries(
                time = List(dataPoints) { it },
                distance = List(dataPoints) { it + applyVariability(5.0, 1.5).toFloat() },
                latlng = List(dataPoints) {
                    Location(
                            50.0F + applyVariability(0.003, 0.0009).toFloat(),
                            51.0F + applyVariability(0.003, 0.0009).toFloat(),
                    )
                },
                altitude = List(dataPoints) { applyVariability(2000.0, 5.0).toFloat() },
                velocity = List(dataPoints) { applyVariability(5.0, 1.5).toFloat() },
                heartrate = List(dataPoints) { applyVariability(145, 45) },
                cadence = List(dataPoints) { applyVariability(70, 40) },
                power = List(dataPoints) { applyVariability(180, 180) },
        )

        return Workout(
                id = UUID.randomUUID(),
                type = WorkoutType.Bike,
                athleteId = UUID.randomUUID(),
                startDateTime = LocalDateTime.now(),
                stravaId = 1234,
                dataSeries = ds,
                metrics = MetricsFactory.createMetricsFor(ds)
        )
    }

    private fun applyVariability(number: Double, magnitude: Double): Double {
        return (number + Random(System.nanoTime()).nextDouble(-magnitude, magnitude))
    }

    private fun applyVariability(number: Int, magnitude: Int): Int {
        return (number + Random(System.nanoTime()).nextInt(-magnitude, magnitude))
    }
}