package pl.edu.pw.mini.velobackend.domain.metrics

data class PowerCurve (
        val times: List<Int>,
        val powers: List<Int?>
){
    companion object{
        fun empty() = PowerCurve(emptyList(), emptyList())
    }
}