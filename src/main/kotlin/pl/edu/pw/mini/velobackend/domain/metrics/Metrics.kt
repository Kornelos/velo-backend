package pl.edu.pw.mini.velobackend.domain.metrics

import java.time.Duration

data class Metrics(
        val totalElapsedTime: Duration?,
        val totalMovingTime: Duration?,
        val totalDistance: Double?,
        val totalWork: Int?,
        val totalCalories: Int?,
        val avgSpeed: Double?,
        val maxSpeed: Double?,
        val avgPower: Int?,
        val maxPower: Int?,
        val totalAscent: Int?,
        val totalDescent: Int?,
        val normalizedPower: Int?,
        val avgHeartRate: Int?,
        val maxHeartRate: Int?,
        val avgCadence: Int?,
        val maxCadence: Int?
) {
    data class Builder(
            var totalElapsedTime: Duration?,
            var totalMovingTime: Duration?,
            var totalDistance: Double?,
            var totalWork: Int?,
            var totalCalories: Int?,
            var avgSpeed: Double?,
            var maxSpeed: Double?,
            var avgPower: Int?,
            var maxPower: Int?,
            var totalAscent: Int?,
            var totalDescent: Int?,
            var normalizedPower: Int?,
            var avgHeartRate: Int?,
            var maxHeartRate: Int?,
            var avgCadence: Int?,
            var maxCadence: Int?
    ) {
        fun totalElapsedTime(totalElapsedTime: Duration) = apply { this.totalElapsedTime = totalElapsedTime }
        fun totalMovingTime(totalMovingTime: Duration) = apply { this.totalMovingTime = totalMovingTime }
        fun totalDistance(totalDistance: Double) = apply { this.totalDistance = totalDistance }
        fun totalWork(totalWork: Int) = apply { this.totalWork = totalWork }
        fun totalCalories(totalCalories: Int) = apply { this.totalCalories = totalCalories }
        fun avgSpeed(avgSpeed: Double) = apply { this.avgSpeed = avgSpeed }
        fun avgPower(avgPower: Int) = apply { this.avgPower = avgPower }
        fun maxSpeed(maxSpeed: Double) = apply { this.maxSpeed = maxSpeed }
        fun maxPower(maxPower: Int) = apply { this.maxPower = maxPower }
        fun totalAscent(totalAscent: Int) = apply { this.totalAscent = totalAscent }
        fun totalDescent(totalDescent: Int) = apply { this.totalDescent = totalDescent }
        fun normalizedPower(normalizedPower: Int) = apply { this.normalizedPower = normalizedPower }
        fun avgHeartRate(avgHeartRate: Int) = apply { this.avgHeartRate = avgHeartRate }
        fun maxHeartRate(maxHeartRate: Int) = apply { this.maxHeartRate = maxHeartRate }
        fun avgCadence(avgCadence: Int) = apply { this.avgCadence = avgCadence }
        fun maxCadence(maxCadence: Int) = apply { this.maxCadence = maxCadence }
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