package pl.edu.pw.mini.velobackend.domain.workout

import java.util.*

interface WorkoutRepository {
    fun getWorkoutById(id: UUID): Workout
    fun getWorkouts(): Collection<Workout>
    fun addWorkout(workout: Workout)
}