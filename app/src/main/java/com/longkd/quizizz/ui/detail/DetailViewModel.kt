package com.longkd.quizizz.ui.detail

import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.toObject
import com.longkd.quizizz.firebase.FirebaseRepository
import com.longkd.quizizz.firebase.FirebaseUserLiveData
import com.longkd.quizizz.model.QuizListModel
import com.longkd.quizizz.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var firebaseRepository: FirebaseRepository,
    firebaseUser: FirebaseUserLiveData
) : ViewModel() {
    val currentUser: LiveData<User> = firebaseUser.map { user ->
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

    private val _quizListModel = MutableLiveData<QuizListModel>()
    val quiz: LiveData<QuizListModel>
        get() = _quizListModel

    private val _startQuizData = MutableLiveData<QuizListModel?>()
    private val _resultPercentage = MutableLiveData<Long>()

    val startQuizData: LiveData<QuizListModel?>
        get() = _startQuizData

    val resultPercentage: LiveData<Long>
        get() = _resultPercentage

    fun setQuizDetail(quizListModel: QuizListModel, user: User) {
        viewModelScope.launch {
            getResult(quizListModel, user)
        }
    }

    fun startQuiz(quizListModel: QuizListModel) {
        _startQuizData.value = quizListModel
    }

    fun startQuizComplete() {
        _startQuizData.value = null
    }

    private suspend fun getResult(quizListModel: QuizListModel, user: User) {
        _quizListModel.value = quizListModel

        withContext(Dispatchers.IO) {
            val value =
                firebaseRepository.getResultsByQuizId(
                    quizListModel.quiz_id,
                    user.userId
                )

            val result = value?.toObject<com.longkd.quizizz.model.Result>()

            // calculate progress
            if (result != null) {
                val total = result.correct + result.wrong + result.unanswered
                val percent = (result.correct * 100) / total

                _resultPercentage.postValue(percent)
            }
        }
    }

}