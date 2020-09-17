package pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams

import kotlinx.serialization.Serializable

@Serializable
data class TemperatureStream(
        val data: List<Int>? = null,
        val original_size: Int? = null,
        val resolution: Resolution? = null,
        val series_type: SeriesType? = null,
)
