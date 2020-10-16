package pl.edu.pw.mini.velobackend.api.workout

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import java.util.UUID

@RestController
@Tag(name = "Workout")
class WorkoutEndpoint(val workoutRepository: WorkoutRepository) {

    @GetMapping("/workout", produces = ["application/json"])
    fun getWorkout(@RequestParam workoutId: UUID): Workout? {
        return workoutRepository.getWorkoutById(workoutId)
    }


}