package pl.edu.pw.mini.velobackend.domain.metrics

object PowerCurveCalculator {

    val times = listOf(
            1..59,
            60..115 step 5,
            120..300 step 10,
            330..570 step 30,
            600..3540 step 60,
            3600..7500 step 300
    ).flatten()

    fun calculate(power: List<Int>, workoutDuration: Int): Map<Int, Int?> {
        return if (power.isEmpty()) {
            emptyMap()
        } else {
            times.filter { it <= workoutDuration }.map { timePoint ->
                timePoint to (power.windowed(timePoint, getStep(timePoint)).map { it.average() }.maxOrNull()?.toInt())
            }.toMap()
        }
    }


    private fun getStep(timePoint: Int): Int =
            when {
                timePoint < 300 -> 1
                timePoint < 1200 -> 5
                else -> 60
            }
}