package pl.edu.pw.mini.velobackend.api.strava.utils

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.common.FileSource
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.ResponseDefinition
import pl.edu.pw.mini.velobackend.api.strava.utils.WorkoutImportTestData.streamsResponses

class StreamTransformer : ResponseDefinitionTransformer() {
    override fun transform(request: Request, responseDefinition: ResponseDefinition, files: FileSource, parameters: Parameters): ResponseDefinition {
        val path = request.url
        val id: Long = path.split('/')[2].toLong()
        return ResponseDefinitionBuilder()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(streamsResponses[id])
                .build()
    }

    override fun getName(): String = "stream"

    override fun applyGlobally(): Boolean = false
}