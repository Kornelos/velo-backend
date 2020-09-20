package pl.edu.pw.mini.velobackend.domain.metrics

import java.time.Duration

data class Metrics(
        val totalElapsedTime: Duration?,
        val totalMovingTime: Duration?,
        val totalDistance: Float?,
        val totalWork: Float?,
        val totalCalories: Float?,
        val avgSpeed: Float?,
        val maxSpeed: Float?,
        val avgPower: Float?,
        val maxPower: Int?,
        val totalAscent: Float?,
        val totalDescent: Float?,
        val normalizedPower: Int?,
        val avgHeartRate: Float?,
        val maxHeartRate: Int?,
        val avgCadence: Float?,
        val maxCadence: Int?
) {
    class Builder {
        private var totalElapsedTime: Duration? = null
        private var totalMovingTime: Duration? = null
        private var totalDistance: Float? = null
        private var totalWork: Float? = null
        private var totalCalories: Float? = null
        private var avgSpeed: Float? = null
        private var maxSpeed: Float? = null
        private var avgPower: Float? = null
        private var maxPower: Int? = null
        private var totalAscent: Float? = null
        private var totalDescent: Float? = null
        private var normalizedPower: Int? = null
        private var avgHeartRate: Float? = null
        private var maxHeartRate: Int? = null
        private var avgCadence: Float? = null
        private var maxCadence: Int? = null

        fun totalElapsedTime(totalElapsedTime: Duration?) = apply { this.totalElapsedTime = totalElapsedTime }
        fun totalMovingTime(totalMovingTime: Duration?) = apply { this.totalMovingTime = totalMovingTime }
        fun totalDistance(totalDistance: Float?) = apply { this.totalDistance = totalDistance }
        fun totalWork(totalWork: Float?) = apply { this.totalWork = totalWork }
        fun totalCalories(totalCalories: Float?) = apply { this.totalCalories = totalCalories }
        fun avgSpeed(avgSpeed: Float?) = apply { this.avgSpeed = avgSpeed }
        fun avgPower(avgPower: Float?) = apply { this.avgPower = avgPower }
        fun maxSpeed(maxSpeed: Float?) = apply { this.maxSpeed = maxSpeed }
        fun maxPower(maxPower: Int?) = apply { this.maxPower = maxPower }
        fun totalAscent(totalAscent: Float?) = apply { this.totalAscent = totalAscent }
        fun totalDescent(totalDescent: Float?) = apply { this.totalDescent = totalDescent }
        fun normalizedPower(normalizedPower: Int?) = apply { this.normalizedPower = normalizedPower }
        fun avgHeartRate(avgHeartRate: Float?) = apply { this.avgHeartRate = avgHeartRate }
        fun maxHeartRate(maxHeartRate: Int?) = apply { this.maxHeartRate = maxHeartRate }
        fun avgCadence(avgCadence: Float?) = apply { this.avgCadence = avgCadence }
        fun maxCadence(maxCadence: Int?) = apply { this.maxCadence = maxCadence }
        fun build() = Metrics(
                totalElapsedTime,
                totalMovingTime,
                totalDistance,
                totalWork,
                totalCalories,
                avgSpeed,
                maxSpeed,
                avgPower,
                maxPower,
                totalAscent,
                totalDescent,
                normalizedPower,
                avgHeartRate,
                maxHeartRate,
                avgCadence,
                maxCadence
        )
    }
}