package pl.edu.pw.mini.velobackend.domain.metrics

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should have key`
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.jupiter.api.Test

class PowerCurveCalculatorTest{

    @Test
    fun `should calculate power curve for workout`(){
        //given
        val workout = MockWorkoutFactory().createMockTrainingWithMetrics(3600)

        //when
        val powerCurve = PowerCurveCalculator.calculate(workout.dataSeries.power, workout.metrics?.totalMovingTime?.toSeconds()!!.toInt())

        //then
        powerCurve.shouldNotBeEmpty()
    }

    @Test
    fun `should contain correct values for power curve`(){
        //given
        val powers = listOf(400,800,200,100,1000)

        //when
        val powerCurve = PowerCurveCalculator.calculate(powers,powers.size)

        //then
        powerCurve[1] `should be equal to` 1000
        powerCurve[2] `should be equal to` 600
        powerCurve[3] `should be equal to` 466
        powerCurve[4] `should be equal to` 525
        powerCurve[5] `should be equal to` powers.average().toInt()
    }
}