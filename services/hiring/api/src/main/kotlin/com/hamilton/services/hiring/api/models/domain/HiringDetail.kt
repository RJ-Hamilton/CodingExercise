package com.hamilton.services.hiring.api.models.domain

import com.hamilton.services.hiring.api.models.data.HiringResponse

data class HiringDetail(
    val id: Int,
    val listId: Int,
    val name: String?
) {
    companion object {
        /**
         * Maps a `HiringResponse` object to a `HiringDetail` object.
         *
         * This function takes a `HiringResponse` object and transforms it into a `HiringDetail`
         * object.
         *
         * @param hiringResponse The `HiringResponse` object to be mapped.
         *
         * @return HiringDetail A new instance of `HiringDetail` with the `id`, `listId`, and `name`
         * properties copied from the corresponding fields in the `HiringResponse`.
         */
        fun mapFromHiringResponse(hiringResponse: HiringResponse): HiringDetail {
            return HiringDetail(
                id = hiringResponse.id,
                listId = hiringResponse.listId,
                name = hiringResponse.name
            )
        }
    }
}
