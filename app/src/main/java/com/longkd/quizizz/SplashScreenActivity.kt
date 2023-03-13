package com.longkd.quizizz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.longkd.quizizz.utils.checkAllMatched
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SplashScreenActivityViewModel by viewModels()

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.launchDestination.collect { destination ->
                    when (destination.getContentIfNotHandled()) {
                        LaunchDestination.MAIN_ACTIVITY -> {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        LaunchDestination.LOGIN -> {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                        else -> {}
                    }.checkAllMatched
                    finish()
                }
            }

        }
    }
}