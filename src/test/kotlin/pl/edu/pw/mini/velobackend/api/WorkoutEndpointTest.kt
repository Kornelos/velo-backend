package pl.edu.pw.mini.velobackend.api

import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not contain`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import pl.edu.pw.mini.velobackend.domain.metrics.MockWorkoutFactory
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import java.time.Duration
import java.time.Instant
import java.util.UUID

class WorkoutEndpointTest : BasicEndpointTest() {

    @Autowired
    lateinit var workoutRepository: WorkoutRepository

    @Test
    fun `should return workout`() {
        //given
        val workout = MockWorkoutFactory().createMockTrainingWithMetrics(1000)
        workoutRepository.addWorkout(workout)

        //when
        val response = mockMvc.perform(get("/workout").queryParam("workoutId", workout.id.toString())).andReturn().response

        //then
        response.contentAsString `should contain` workout.athleteId.toString()
        response.contentAsString `should contain` workout.name
    }

    @Test
    fun `should return workout from given time range`() {
        //given
        val athleteId = UUID.randomUUID()
        val workouts = mapOf(
                0 to MockWorkoutFactory().createMockTrainingWithMetrics(10, athleteId, 0),
                1 to MockWorkoutFactory().createMockTrainingWithMetrics(10, athleteId, 1),
                2 to MockWorkoutFactory().createMockTrainingWithMetrics(10, athleteId, 2),
                3 to MockWorkoutFactory().createMockTrainingWithMetrics(10, athleteId, 3),
                4 to MockWorkoutFactory().createMockTrainingWithMetrics(10, UUID.randomUUID(), 1)
        )
        workouts.forEach { (_, workout) -> workoutRepository.addWorkout(workout) }

        //when
        val response = mockMvc.perform(get("/workouts")
                .queryParam("athleteId", athleteId.toString())
                .queryParam("beforeEpoch", Instant.now().plus(Duration.ofDays(1)).epochSecond.toString())
                .queryParam("afterEpoch", Instant.now().minus(Duration.ofDays(2)).epochSecond.toString())

        ).andReturn().response

        //then
        response.contentAsString `should contain` workouts[0]?.id.toString()
        response.contentAsString `should contain` workouts[1]?.id.toString()
        response.contentAsString `should contain` workouts[2]?.id.toString()
        response.contentAsString `should not contain` workouts[3]?.id.toString()
        response.contentAsString `should not contain` workouts[4]?.id.toString()
    }

    @Test
    fun `should return workout metadata`() {
        //given
        val workout = MockWorkoutFactory().createMockTrainingWithMetrics(1000)
        workoutRepository.addWorkout(workout)

        //when
        val response = mockMvc.perform(get("/workouts-metadata").queryParam("athleteId", workout.athleteId.toString())).andReturn().response

        //then
        response.contentAsString `should contain` workout.id.toString()
        response.contentAsString `should contain` workout.name

    }


}