package com.longkd.quizizz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.longkd.quizizz.firebase.FirebaseUserLiveData
import com.longkd.quizizz.result.Event
import com.longkd.quizizz.ui.theme.ThemedActivityDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    firebaseUserLiveData: FirebaseUserLiveData,
    themedActivityDelegate: ThemedActivityDelegate
) : ViewModel(),
    ThemedActivityDelegate by themedActivityDelegate {

    val currentUser = firebaseUserLiveData

    private val _navigateToSignOutDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignOutDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignOutDialogAction

    fun onProfileClicked() {
        _navigateToSignOutDialogAction.value = Event(Unit)
    }
}