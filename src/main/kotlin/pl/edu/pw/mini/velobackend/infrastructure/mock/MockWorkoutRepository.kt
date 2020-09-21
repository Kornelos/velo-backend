package pl.edu.pw.mini.velobackend.infrastructure.mock

import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import java.util.UUID

@Repository
class MockWorkoutRepository : WorkoutRepository {

    private val workouts = ArrayList<Workout>()

    override fun getWorkoutById(id: UUID): Workout = workouts.first { it.athleteId == id }

    override fun getWorkouts(): Collection<Workout> = workouts

    override fun addWorkout(workout: Workout) {
        workouts.add(workout)
    }

    override fun workoutExists(stravaId: Long): Boolean = workouts.any { it.stravaId == stravaId }

    fun removeAll() {
        workouts.clear()
    }

}