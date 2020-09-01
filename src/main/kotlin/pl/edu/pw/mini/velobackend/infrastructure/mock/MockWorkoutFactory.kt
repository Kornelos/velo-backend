package pl.edu.pw.mini.velobackend.infrastructure.mock

import pl.edu.pw.mini.velobackend.domain.metrics.MetricsFactory
import pl.edu.pw.mini.velobackend.domain.model.Location
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRecord
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutType
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MockWorkoutFactory {
    fun createMockTrainingWithMetrics(durationSeconds: Int): Workout {
        val mockLocation = Location(50.0, 51.0)
        val mockAltitude = 2000.0
        val mockSpeed = 9.0
        val mockHR = 145
        val mockCadence = 80
        val mockPower = 180
        var speed: Double
        var distance = 0.0
        val startTime = LocalDateTime.now()
        val workoutRecords = List(durationSeconds + 1) {
            speed = applyVariability(mockSpeed, 1.0)
            distance += speed
            WorkoutRecord(
                    mockLocation,
                    startTime.plusSeconds(it.toLong()),
                    distance,
                    applyVariability(mockAltitude, 5.0),
                    speed,
                    applyVariability(mockHR, 45),
                    applyVariability(mockCadence, 20),
                    applyVariability(mockPower, 180)
            )

        }
        return Workout(
                UUID.randomUUID(), workoutRecords, ArrayList(), WorkoutType.RoadBike,
                MetricsFactory.createMetricsFrom(workoutRecords)
        )
    }

    private fun applyVariability(number: Double, magnitude: Double): Double {
        return (number + Random(System.nanoTime()).nextDouble(-magnitude, magnitude))
    }

    private fun applyVariability(number: Int, magnitude: Int): Int {
        return (number + Random(System.nanoTime()).nextInt(-magnitude, magnitude))
    }
}