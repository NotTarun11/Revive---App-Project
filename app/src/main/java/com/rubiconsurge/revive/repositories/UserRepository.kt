package com.rubiconsurge.revive.repositories

import com.rubiconsurge.revive.local.RoomDao
import com.rubiconsurge.revive.models.User
import com.rubiconsurge.revive.sealed.DataResponse
import com.rubiconsurge.revive.sealed.Error
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dao: RoomDao,
) {
    /** Fake login operation with email and password */
    suspend fun signInUser(email: String, password: String): DataResponse<User> {
        return dao.fakeSignIn(email = email, password = password)?.let {
            DataResponse.Success(data = it)
        } ?: DataResponse.Error(error = Error.Empty)
    }

    /** Save the user to local storage */
    suspend fun saveUserLocally(user: User) {
        dao.saveUser(user = user)
    }

    /** Get current logged user from local */
    suspend fun getLoggedUser(userId: Int) = dao.getLoggedUser(userId = userId)

    /** Get the available payment providers for current user */
    suspend fun getUserPaymentProviders() =
        dao.getUserPaymentProviders()

    /** Get the available locations for current user */
    suspend fun getUserLocations() = dao.getUserLocations()
}