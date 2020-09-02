package pl.edu.pw.mini.velobackend.domain.metrics

import org.amshove.kluent.`should not be null`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRecord
import pl.edu.pw.mini.velobackend.infrastructure.mock.MockWorkoutFactory
import java.time.Duration
import java.time.LocalDateTime

class MetricsFactoryTest {

    @Test
    fun `should calculate basic metrics of workout`() {
        //given
        val time = LocalDateTime.now()
        val records = listOf(
                WorkoutRecord(null, time, 0.0, 1000.0, 20.0, 100, 60, 200),
                WorkoutRecord(null, time.plusHours(1), 25.0, 2000.0, 30.0, 200, 100, 400)
        )
        //when
        val metrics = MetricsFactory.createMetricsFrom(records)

        //then
        metrics.totalElapsedTime shouldBeEqualTo Duration.ofHours(1)
        metrics.totalDistance shouldBeEqualTo 25.0
        metrics.totalAscent shouldBeEqualTo 1000
        metrics.totalDescent shouldBeEqualTo 0
        metrics.avgSpeed shouldBeEqualTo 25.0
        metrics.maxSpeed shouldBeEqualTo 30.0
        metrics.avgHeartRate shouldBeEqualTo 150
        metrics.maxHeartRate shouldBeEqualTo 200
        metrics.avgCadence shouldBeEqualTo 80
        metrics.maxCadence shouldBeEqualTo 100
        metrics.avgPower shouldBeEqualTo 300
        metrics.maxPower shouldBeEqualTo 400
    }

    @Test
    fun `should calculate advanced metrics`() {
        //given
        val workout = MockWorkoutFactory().createMockTrainingWithMetrics(3600)

        //when
        val metrics = MetricsFactory.createMetricsFrom(workout.workoutRecords)

        //then
        metrics.normalizedPower.`should not be null`()
        metrics.totalWork.`should not be null`()
        metrics.totalCalories.`should not be null`()
    }

    //TODO: add tests after adding FIT support
}