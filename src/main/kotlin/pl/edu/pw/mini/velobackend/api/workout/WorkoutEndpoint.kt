package pl.edu.pw.mini.velobackend.api.workout

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import pl.edu.pw.mini.velobackend.infrastructure.workout.WorkoutMeta
import pl.edu.pw.mini.velobackend.infrastructure.workout.toWorkoutMeta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
@RestController
@Tag(name = "Workout")
class WorkoutEndpoint(val workoutRepository: WorkoutRepository) {

    @GetMapping("/workout", produces = ["application/json"])
    fun getWorkout(@RequestParam workoutId: UUID): Workout? {
        return workoutRepository.getWorkoutById(workoutId)
    }

    @GetMapping("/workouts",produces = ["application/json"])
    fun getWorkouts(@RequestParam athleteId: UUID, @RequestParam beforeEpoch: Long, @RequestParam afterEpoch: Long): Collection<Workout> {
        return workoutRepository.getWorkoutsForAthleteIdWithinRange(athleteId, LocalDateTime.ofEpochSecond(beforeEpoch,0, ZoneOffset.UTC),LocalDateTime.ofEpochSecond(afterEpoch,0, ZoneOffset.UTC))
    }

    @GetMapping("/workouts-metadata", produces = ["application/json"])
    fun getWorkoutsMetadata(@RequestParam athleteId: UUID): List<WorkoutMeta> = workoutRepository.getWorkoutsForAthleteId(athleteId).map { it.toWorkoutMeta() }
}