package com.hamilton.services.hiring.api

import com.hamilton.services.hiring.api.models.data.HiringResponse
import retrofit2.Response
import retrofit2.http.GET

interface HiringApi {
    @GET("hiring.json")
    suspend fun getFetchData(
    ): Response<List<HiringResponse>>
}
