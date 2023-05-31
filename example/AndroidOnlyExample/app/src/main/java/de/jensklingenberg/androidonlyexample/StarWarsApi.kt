package de.jensklingenberg.androidonlyexample

import de.jensklingenberg.ktorfit.Call
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.coroutines.flow.Flow

interface StarWarsApi {

    @GET("application")
    suspend fun getApplications(): Array<KtorfitApplicationShort>
}