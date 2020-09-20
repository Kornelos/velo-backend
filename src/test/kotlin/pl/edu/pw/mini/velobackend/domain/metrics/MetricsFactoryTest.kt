package pl.edu.pw.mini.velobackend.domain.metrics

import org.amshove.kluent.`should not be null`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import pl.edu.pw.mini.velobackend.domain.workout.DataSeries
import pl.edu.pw.mini.velobackend.infrastructure.mock.MockWorkoutFactory
import java.time.Duration

class MetricsFactoryTest {

    @Test
    fun `should calculate basic metrics of workout`() {
        //given
        val dataSeries = DataSeries(
                time = listOf(0, 3600),
                distance = listOf(0.0F, 25.0F),
                latlng = emptyList(),
                altitude = listOf(1000.0F, 2000.0F),
                velocity = listOf(20.0F, 30.0F),
                heartrate = listOf(100, 200),
                cadence = listOf(60, 100),
                power = listOf(200, 400)
        )
        //when
        val metrics = MetricsFactory.createMetricsFor(dataSeries)

        //then
        metrics.totalElapsedTime shouldBeEqualTo Duration.ofHours(1)
        metrics.totalDistance shouldBeEqualTo 25.0F
        metrics.totalAscent shouldBeEqualTo 1000.0F
        metrics.totalDescent shouldBeEqualTo 0.0F
        metrics.avgSpeed shouldBeEqualTo 25.0F
        metrics.maxSpeed shouldBeEqualTo 30.0F
        metrics.avgHeartRate shouldBeEqualTo 150.0F
        metrics.maxHeartRate shouldBeEqualTo 200
        metrics.avgCadence shouldBeEqualTo 80.0F
        metrics.maxCadence shouldBeEqualTo 100
        metrics.avgPower shouldBeEqualTo 300.0F
        metrics.maxPower shouldBeEqualTo 400
    }

    @Test
    fun `should calculate advanced metrics`() {
        //given
        val workout = MockWorkoutFactory().createMockTrainingWithMetrics(3600)

        //when
        val metrics = MetricsFactory.createMetricsFor(workout)

        //then
        metrics.normalizedPower.`should not be null`()
        metrics.totalWork.`should not be null`()
        metrics.totalCalories.`should not be null`()
    }

    //TODO: add tests after adding FIT support
}