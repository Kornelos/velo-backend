package pl.edu.pw.mini.velobackend.infrastructure.mock

import org.amshove.kluent.`should not be null`
import org.junit.jupiter.api.Test

class MockWorkoutTest {

    @Test
    fun `should create mock workout`() {
        //given
        val mockWorkoutFactory = MockWorkoutFactory()

        //when
        val workout = mockWorkoutFactory.createMockTrainingWithMetrics(3600)

        //then
        workout.`should not be null`()
    }
}