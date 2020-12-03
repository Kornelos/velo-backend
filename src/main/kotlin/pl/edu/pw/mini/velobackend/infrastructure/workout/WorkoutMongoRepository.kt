package pl.edu.pw.mini.velobackend.infrastructure.workout

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pl.edu.pw.mini.velobackend.domain.workout.Workout
import pl.edu.pw.mini.velobackend.domain.workout.WorkoutRepository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class WorkoutMongoRepository(
        val mongoTemplate: MongoTemplate
) : WorkoutRepository {
    override fun getWorkoutById(id: UUID): Workout? = findWorkoutByKey("id", id)

    override fun getWorkouts(): Collection<Workout> {
        TODO("Not yet implemented")
    }

    override fun addWorkout(workout: Workout) {
        mongoTemplate.save(workout.toWorkoutDto())
    }

    override fun workoutExists(athleteId: UUID, stravaId: Long): Boolean = mongoTemplate.findOne(
            Query.query(Criteria.where("stravaId").`is`(stravaId).and("athleteId").`is`(athleteId)), WorkoutDto::class.java) != null

    override fun getWorkoutsForAthleteId(athleteId: UUID): Collection<Workout> = mongoTemplate.find(
            Query.query(Criteria.where("athleteId").`is`(athleteId)), WorkoutDto::class.java).map { it.toWorkout() }

    override fun getWorkoutsForAthleteIdWithinRange(athleteId: UUID, before: LocalDateTime, after: LocalDateTime) = mongoTemplate.find(
    Query.query(Criteria.where("athleteId").`is`(athleteId).and("startDateTime").gte(after).lt(before)), WorkoutDto::class.java).map { it.toWorkout() }


    private fun findWorkoutByKey(keyName: String, value: Any): Workout? {
        val workoutDto = mongoTemplate.findOne(Query.query(
                Criteria.where(keyName).`is`(value)), WorkoutDto::class.java
        )
        return workoutDto?.toWorkout()
    }
}