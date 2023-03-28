package com.longkd.quizizz.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.longkd.quizizz.databinding.FragmentQuizBinding
import com.longkd.quizizz.ui.MainNavigationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentQuizBinding
    private val args: QuizFragmentArgs by navArgs()
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        quizViewModel.user.observe(viewLifecycleOwner) { user ->
            quizViewModel.initializeQuestions(args.quizData, user)
        }

        binding = FragmentQuizBinding.inflate(inflater, container, false).apply {
            viewModel = quizViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}