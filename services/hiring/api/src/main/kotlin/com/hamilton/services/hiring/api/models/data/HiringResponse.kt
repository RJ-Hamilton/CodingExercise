package com.hamilton.services.hiring.api.models.data

import kotlinx.serialization.Serializable

@Serializable
data class HiringResponse(
    val id: Int,
    val listId: Int,
    val name: String?
)
