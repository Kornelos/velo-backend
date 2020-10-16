package pl.edu.pw.mini.velobackend.domain.workout

import java.util.UUID

interface WorkoutRepository {
    fun getWorkoutById(id: UUID): Workout?
    fun getWorkouts(): Collection<Workout>
    fun addWorkout(workout: Workout)
    fun workoutExists(stravaId: Long): Boolean
    fun getWorkoutsForAthleteId(athleteId: UUID): Collection<Workout>
}