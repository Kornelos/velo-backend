package pl.edu.pw.mini.velobackend.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.infrastructure.mock.MockWorkoutFactory


@RestController
class MockWorkoutEndpoint {

    @GetMapping("/mock-workout")
    fun getWorkout(@RequestParam(defaultValue = "3600") seconds: Int): ResponseEntity<Workout> =
            ResponseEntity.ok(MockWorkoutFactory().createMockTrainingWithMetrics(seconds))
}