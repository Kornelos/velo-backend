package pl.edu.pw.mini.velobackend.api

import org.amshove.kluent.`should contain`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import pl.edu.pw.mini.velobackend.domain.metrics.MockWorkoutFactory
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository

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