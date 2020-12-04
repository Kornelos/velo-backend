package pl.edu.pw.mini.velobackend.domain.workout

import java.time.LocalDateTime
import java.util.UUID

interface WorkoutRepository {
    fun getWorkoutById(id: UUID): Workout?
    fun getWorkouts(): Collection<Workout>
    fun addWorkout(workout: Workout)
    fun workoutExists(athleteId: UUID, stravaId: Long): Boolean
    fun getWorkoutsForAthleteId(athleteId: UUID): Collection<Workout>
    fun getWorkoutsForAthleteIdWithinRange(athleteId: UUID, before: LocalDateTime, after: LocalDateTime): Collection<Workout>
}