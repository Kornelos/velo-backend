package pl.edu.pw.mini.velobackend.domain.athlete

import java.time.LocalDate
import java.util.UUID

data class Athlete(
        val id: UUID,
        val name: String?,
        val age: Int?,
        val maxHeartRate: Int?,
        val gender: String?,
        val height: Int?,
        val weights: Map<LocalDate, Int> = HashMap(),
        val ftps: Map<LocalDate, Int> = HashMap(),
        val thresholdHeartRates: Map<LocalDate, Int> = HashMap()
) {
    class Builder {
        private var id: UUID = UUID.randomUUID()
        private var name: String? = null
        private var age: Int? = null
        private var maxHeartRate: Int? = null
        private var gender: String? = null
        private var height: Int? = null
        private var weights: Map<LocalDate, Int> = HashMap()
        private var ftps: Map<LocalDate, Int> = HashMap()
        private var thresholdHeartRates: Map<LocalDate, Int> = HashMap()

        fun id(id: UUID) = apply { this.id = id }
        fun build() = Athlete(id, name, age, maxHeartRate, gender, height, weights, ftps, thresholdHeartRates)
    }
}