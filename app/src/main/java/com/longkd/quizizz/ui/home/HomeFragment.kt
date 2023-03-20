package com.longkd.quizizz.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.longkd.quizizz.MainActivityViewModel
import com.longkd.quizizz.databinding.FragmentHomeBinding
import com.longkd.quizizz.login.setupProfileMenuItem
import com.longkd.quizizz.ui.MainNavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it.userId.isNotEmpty()) {
                homeViewModel.fetchQuizList(it.userId)
            }
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel

        }

        (activity as AppCompatActivity?)!!.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        val recommendedQuizListAdapter = HomeQuizListAdapter {
            Timber.d("Quiz Clicked")
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it
                )
            )
        }

        val popularQuizListAdapter = HomeQuizListAdapter {
            Timber.d("Quiz Clicked")
            this.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it
                )
            )
        }

        binding.homeForYouList.adapter = recommendedQuizListAdapter
        homeViewModel.recommendedQuizList.observe(viewLifecycleOwner, Observer {
            it.let {
                recommendedQuizListAdapter.submitList(it)
            }
        })

        binding.homeTrendingList.adapter = popularQuizListAdapter
        homeViewModel.popularQuizList.observe(viewLifecycleOwner, Observer {
            it.let {
                popularQuizListAdapter.submitList(it)
            }
        })

        binding.homePlayButton.setOnClickListener {
            homeViewModel.playQuiz()
        }

        homeViewModel.navigateToQuizListModel.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        it
                    )
                )
                homeViewModel.playQuizComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        binding.toolbar.setupProfileMenuItem(menu, inflater, mainActivityViewModel, this)
    }
}