package pl.edu.pw.mini.velobackend.infrastructure.strava.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.edu.pw.mini.velobackend.infrastructure.serialization.LocalDateTimeIsoSerializer
import java.time.LocalDateTime

@Serializable
data class SummaryActivity(
        var id: Long? = null,

        @SerialName("external_id")
        var externalId: String? = null,

        @SerialName("upload_id")
        val uploadId: Long? = null,

        val athlete: MetaAthlete? = null,

        val name: String? = null,

        val distance: Float? = null,

        @SerialName("moving_time")
        val movingTime: Int? = null,

        @SerialName("elapsed_time")
        val elapsedTime: Int? = null,

        @SerialName("total_elevation_gain")
        val totalElevationGain: Float? = null,

        @SerialName("elev_high")
        val elevHigh: Float? = null,

        @SerialName("elev_low")
        val elevLow: Float? = null,

        val type: ActivityType? = null,

        @Serializable(with = LocalDateTimeIsoSerializer::class) @SerialName("start_date")
        val startDate: LocalDateTime? = null,

        @Serializable(with = LocalDateTimeIsoSerializer::class) @SerialName("start_date_local")
        val startDateLocal: LocalDateTime? = null,

        val timezone: String? = null,

        @SerialName("start_latlng")
        val startLatlng: List<Float>? = null,

        @SerialName("end_latlng")
        val endLatlng: List<Float>? = null,

        @SerialName("achievement_count")
        val achievementCount: Int? = null,

        @SerialName("kudos_count")
        val kudosCount: Int? = null,

        @SerialName("comment_count")
        val commentCount: Int? = null,

        @SerialName("athlete_count")
        val athleteCount: Int? = null,

        @SerialName("photo_count")
        val photoCount: Int? = null,

        @SerialName("total_photo_count")
        val totalPhotoCount: Int? = null,

        val map: PolylineMap? = null,

        val trainer: Boolean? = null,

        val commute: Boolean? = null,

        val manual: Boolean? = null,

        val flagged: Boolean? = null,

        @SerialName("workout_type")
        val workoutType: Int? = null,

        @SerialName("upload_id_str")
        val uploadIdStr: String? = null,

        @SerialName("average_speed")
        val averageSpeed: Float? = null,

        @SerialName("max_speed")
        val maxSpeed: Float? = null,

        @SerialName("has_kudoed")
        val hasKudoed: Boolean? = null,

        @SerialName("gear_id")
        val gearId: String? = null,

        val kilojoules: Float? = null,

        @SerialName("average_watts")
        val averageWatts: Float? = null,

        @SerialName("device_watts")
        val deviceWatts: Boolean? = null,

        @SerialName("max_watts")
        val maxWatts: Int? = null,

        @SerialName("weighted_average_watts")
        val weightedAverageWatts: Int? = null,
)

@Serializable
data class MetaAthlete(val id: Long,
                       @SerialName("resource_state")
                       val resourceState: Int
)

@Serializable
data class PolylineMap(var id: String? = null,
                       val polyline: String? = null,
                       @SerialName("summary_polyline")
                       val summaryPolyline: String? = null
)