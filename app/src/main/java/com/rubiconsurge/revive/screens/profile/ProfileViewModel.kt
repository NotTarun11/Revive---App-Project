package com.rubiconsurge.revive.screens.profile


import androidx.lifecycle.ViewModel
import com.rubiconsurge.revive.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

}