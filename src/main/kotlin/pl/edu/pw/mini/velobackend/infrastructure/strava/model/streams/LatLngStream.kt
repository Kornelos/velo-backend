package pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams

import kotlinx.serialization.Serializable

@Serializable
data class LatLngStream(
        val `data`: List<List<Float>>? = null,
        val original_size: Int? = null,
        val resolution: Resolution? = null,
        val series_type: SeriesType? = null,
)