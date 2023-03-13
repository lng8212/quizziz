package com.longkd.quizizz

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.longkd.quizizz.result.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SplashScreenActivityViewModel @Inject constructor(
) : ViewModel() {

    val launchDestination = flow {
        val firebaseUser = FirebaseAuth.getInstance()
        if (firebaseUser.currentUser != null) {
            emit(Event(LaunchDestination.MAIN_ACTIVITY))
        } else {
            emit(Event(LaunchDestination.LOGIN))
        }
    }
}

enum class LaunchDestination {
    LOGIN,
    MAIN_ACTIVITY
}