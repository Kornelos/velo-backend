package pl.edu.pw.mini.velobackend.infrastructure.strava.model.streams

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamSet(
        var time: TimeStream? = null,

        val distance: DistanceStream? = null,

        @SerialName("latlng")
        val latlng: LatLngStream? = null,

        val altitude: AltitudeStream? = null,

        @SerialName("velocity_smooth")
        val velocitySmooth: SmoothVelocityStream? = null,

        val heartrate: HeartrateStream? = null,

        val cadence: CadenceStream? = null,

        val watts: PowerStream? = null,

        val temp: TemperatureStream? = null,

        val moving: MovingStream? = null,

        @SerialName("grade_smooth")
        val gradeSmooth: SmoothGradeStream? = null
)