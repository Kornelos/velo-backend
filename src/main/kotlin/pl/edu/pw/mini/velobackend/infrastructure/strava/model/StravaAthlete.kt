package pl.edu.pw.mini.velobackend.infrastructure.strava.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StravaAthlete(
        val id: Int,
        val username: String,
        @SerialName("resource_state")
        val resourceState: Int,
        @SerialName("firstname")
        val firstName: String,
        @SerialName("lastname")
        val lastName: String,
        val city: String,
        val state: String,
        val country: String,
        val sex: String,
        val premium: Boolean,
        val summit: Boolean,
        @SerialName("created_at")
        val createdAt: String,
        @SerialName("updated_at")
        val updatedAt: String,
        @SerialName("badge_type_id")
        val badgeTypeId: Int,
        @SerialName("profile_medium")
        val profileMedium: String?,
        val profile: String?,
        val friend: String?,
        val follower: String?
)