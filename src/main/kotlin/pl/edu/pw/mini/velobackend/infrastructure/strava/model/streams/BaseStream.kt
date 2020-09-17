package pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseStream(
        @SerialName("original_size")
        val originalSize: Int? = null,
        val resolution: Resolution? = null,
        @SerialName("series_type")
        val seriesType: SeriesType? = null
)

/**
 * The level of detail (sampling) in which this stream was returned
 * Values: low,medium,high
 */
enum class Resolution(val value: Any) {
    low("low"),
    medium("medium"),
    high("high");
}

/**
 * The base series used in the case the stream was downsampled
 * Values: distance,time
 */
enum class SeriesType(val value: Any) {
    distance("distance"),
    time("time");
}