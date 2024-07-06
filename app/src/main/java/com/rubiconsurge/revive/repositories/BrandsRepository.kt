package com.rubiconsurge.revive.repositories

import com.rubiconsurge.revive.local.RoomDao
import com.rubiconsurge.revive.models.Advertisement
import com.rubiconsurge.revive.models.Manufacturer
import com.rubiconsurge.revive.sealed.DataResponse
import com.rubiconsurge.revive.sealed.Error
import com.rubiconsurge.revive.utils.getStructuredManufacturers
import javax.inject.Inject

class BrandsRepository @Inject constructor(
    private val dao: RoomDao,
) {
    suspend fun getBrandsAdvertisements(): DataResponse<List<Advertisement>> {
        /** First we should check the local storage */
        dao.getAdvertisements().let {
            return if (it.isNotEmpty()) {
                DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                DataResponse.Error(error = Error.Empty)
            }
        }
    }

    suspend fun getBrandsWithProducts(): DataResponse<List<Manufacturer>> {
        /** First we should check the local storage */
        dao.getManufacturersWithProducts().getStructuredManufacturers().let {
            return if (it.isNotEmpty()) {
                DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                DataResponse.Error(error = Error.Empty)
            }
        }
    }
}

