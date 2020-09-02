package pl.edu.pw.mini.velobackend.infrastructure.workout

import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import pl.edu.pw.mini.velobackend.infrastructure.mock.MockWorkoutFactory
import java.util.*

class MockWorkoutRepository : WorkoutRepository {
    private val workoutFactory: MockWorkoutFactory = MockWorkoutFactory()
    private val workouts: MutableList<Workout> = mutableListOf(workoutFactory.createMockTrainingWithMetrics(3600))

    override fun getWorkoutById(id: UUID): Workout = workouts.first { it.id == id }

    override fun getWorkouts(): Collection<Workout> = workouts

    override fun addWorkout(workout: Workout) {
        workouts.add(workout)
    }
}