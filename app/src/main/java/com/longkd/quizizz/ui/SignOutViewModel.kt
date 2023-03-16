package com.longkd.quizizz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.longkd.quizizz.firebase.FirebaseUserLiveData
import com.longkd.quizizz.model.User
import com.longkd.quizizz.result.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignOutViewModel @Inject constructor(private val firebaseUserLiveData: FirebaseUserLiveData) :
    ViewModel() {
    val currentUser: LiveData<User> = firebaseUserLiveData.map { user ->
        if (user != null) {
            User(
                user.uid,
                user.displayName,
                user.photoUrl,
                user.email
            )
        } else {
            User()
        }
    }

    private val _onSignOutAction = MutableLiveData<Event<Unit>>()
    val onSignOutAction: LiveData<Event<Unit>>
        get() = _onSignOutAction

    fun onSignOut() {
        _onSignOutAction.value = Event(Unit)
    }
}