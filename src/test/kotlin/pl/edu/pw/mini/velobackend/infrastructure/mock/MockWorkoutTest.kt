package pl.edu.pw.mini.velobackend.infrastructure.mock

import org.amshove.kluent.`should not be null`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import pl.edu.pw.mini.velobackend.infrastructure.workout.MockWorkoutRepository
import java.time.Duration

class MockWorkoutTest {

    @Test
    fun `should create mock workout`() {
        //given
        val mockWorkoutFactory = MockWorkoutFactory()

        //when
        val workout = mockWorkoutFactory.createMockTrainingWithMetrics(3600)

        //then
        workout.`should not be null`()
        workout.metrics!!.totalElapsedTime!!.toSeconds() shouldBeEqualTo Duration.ofSeconds(3600).toSeconds()
    }

    @Test
    fun `should get workout from mock repository`() {
        //given
        val workoutRepository: WorkoutRepository = MockWorkoutRepository()

        //when
        val workout = workoutRepository.getWorkouts().first()

        //then
        workout.`should not be null`()
    }
}