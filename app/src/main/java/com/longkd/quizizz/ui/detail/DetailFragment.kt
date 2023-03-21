package com.longkd.quizizz.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.longkd.quizizz.databinding.FragmentDetailBinding
import com.longkd.quizizz.ui.MainNavigationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                detailViewModel.setQuizDetail(args.quizData, user)
            }
        }
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.detailsStartBtn.setOnClickListener {
            detailViewModel.startQuiz(args.quizData)
        }
        detailViewModel.startQuizData.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController()
                    .navigate(DetailFragmentDirections.actionDetailFragmentToQuizFragment(args.quizData))
                detailViewModel.startQuizComplete()
            }
        }
        return binding.root
    }
}