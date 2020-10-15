package pl.edu.pw.mini.velobackend.domain.athlete

import java.time.LocalDate
import java.util.UUID

data class Athlete(
        val id: UUID,
        val email: String,
        val firstName: String?,
        val lastName: String?,
        val age: Int?,
        val maxHeartRate: Int?,
        val gender: String?,
        val height: Int?,
        val weights: Map<LocalDate, Int> = HashMap(),
        val ftps: Map<LocalDate, Int> = HashMap(),
        val thresholdHeartRates: Map<LocalDate, Int> = HashMap()
) {
    class Builder(private var email: String) {
        private var id: UUID = UUID.randomUUID()
        private var firstName: String? = null
        private var lastName: String? = null
        private var age: Int? = null
        private var maxHeartRate: Int? = null
        private var gender: String? = null
        private var height: Int? = null
        private var weights: Map<LocalDate, Int> = HashMap()
        private var ftps: Map<LocalDate, Int> = HashMap()
        private var thresholdHeartRates: Map<LocalDate, Int> = HashMap()

        //fun id(id: UUID) = apply { this.id = id }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun age(age: Int) = apply { this.age = age }
        fun gender(gender: String) = apply { this.gender = gender }

        fun build() = Athlete(id, email, firstName, lastName, age, maxHeartRate, gender, height, weights, ftps, thresholdHeartRates)
    }
}