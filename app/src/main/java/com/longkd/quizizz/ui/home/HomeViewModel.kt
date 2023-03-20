package com.longkd.quizizz.ui.home

import androidx.lifecycle.*
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.longkd.quizizz.firebase.FirebaseRepository
import com.longkd.quizizz.firebase.FirebaseUserLiveData
import com.longkd.quizizz.model.QuizListModel
import com.longkd.quizizz.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
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

    private val _featuredQuiz = MutableLiveData<QuizListModel>()
    val featuredQuiz: LiveData<QuizListModel>
        get() = _featuredQuiz

    private val _recommendedQuizList = MutableLiveData<List<QuizListModel>>()
    val recommendedQuizList: LiveData<List<QuizListModel>>
        get() = _recommendedQuizList

    private val _popularQuizList = MutableLiveData<List<QuizListModel>>()
    val popularQuizList: LiveData<List<QuizListModel>>
        get() = _popularQuizList

    private val _navigateToQuizListModel = MutableLiveData<QuizListModel?>()
    val navigateToQuizListModel: LiveData<QuizListModel?>
        get() = _navigateToQuizListModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun fetchQuizList(userId: String) {
        viewModelScope.launch {
            try {
                getQuizList(userId)
            } catch (e: IOException) {
                _recommendedQuizList.value = listOf()
                _popularQuizList.value = listOf()

                Timber.d("No quizzes retrieved")
            }
        }
    }

    private suspend fun getQuizList(userId: String) {
        withContext(Dispatchers.IO) {
            _isLoading.postValue(true)

            val recommendedQuizzes = parseQuizzes(firebaseRepository.getRecommendedQuiz(userId))
            val popularQuizzes = parseQuizzes(firebaseRepository.getMostPopularQuiz(userId))

            if (recommendedQuizzes.size > 0 && popularQuizzes.size > 0) {
                _recommendedQuizList.postValue(recommendedQuizzes)
                _popularQuizList.postValue(popularQuizzes)

                val randomInt = Random.nextInt(0, popularQuizzes.size)
                _featuredQuiz.postValue(popularQuizzes[randomInt])
            } else {
                getQuizList(userId)
//
//                val defaultRecommendedQuizzes = parseQuizzes(firebaseRepository.getQuizList())
//                val defaultPopularQuizzes = parseQuizzes(firebaseRepository.getQuizList())
//
//                _recommendedQuizList.postValue(defaultRecommendedQuizzes)
//                _popularQuizList.postValue(defaultPopularQuizzes)
//
//                val randomInt = Random.nextInt(0, defaultPopularQuizzes.size)
//                _featuredQuiz.postValue(defaultPopularQuizzes[randomInt])

                Timber.d("Retry retrieving quiz")
            }

            _isLoading.postValue(false)
        }
    }

    private fun parseQuizzes(value: QuerySnapshot?): MutableList<QuizListModel> {
        val quizListModelList: MutableList<QuizListModel> = mutableListOf()
        for (doc in value!!) {
            val quizItem = doc.toObject<QuizListModel>()
            quizListModelList.add(quizItem)
        }

        Timber.d("$quizListModelList")
        return quizListModelList
    }

    fun playQuiz() {
        _navigateToQuizListModel.value = featuredQuiz.value
    }

    fun playQuizComplete() {
        _navigateToQuizListModel.value = null
    }

}