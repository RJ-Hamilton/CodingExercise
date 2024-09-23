package com.hamilton.services.hiring.impl

import com.hamilton.services.hiring.api.HiringApi
import com.hamilton.services.hiring.api.HiringRepository
import com.hamilton.services.hiring.api.models.domain.HiringDetail

class HiringRepositoryImpl(
    private val hiringApi: HiringApi
) : HiringRepository {
    override suspend fun getData(): List<HiringDetail> {
        val response = hiringApi.getFetchData()

        return response.body()?.let { listOfResults ->
            listOfResults.map { hiringData ->
                HiringDetail.mapFromHiringResponse(hiringData)
            }
        } ?: throw RuntimeException()
    }
}