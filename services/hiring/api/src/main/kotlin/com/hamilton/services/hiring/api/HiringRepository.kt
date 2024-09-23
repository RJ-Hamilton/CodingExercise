package com.hamilton.services.hiring.api

import com.hamilton.services.hiring.api.models.domain.HiringDetail

interface HiringRepository {

    /**
     * Suspended function to retrieve a list of `HiringDetail` items.
     *
     * @return List<HiringDetail> A list of `HiringDetail` objects.
     */
    suspend fun getData(): List<HiringDetail>
}