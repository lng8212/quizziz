package com.longkd.quizizz

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.longkd.quizizz.databinding.NavHeaderBinding
import com.longkd.quizizz.utils.updateForTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val DIALOG_SIGN_OUT = "dialog_sign_out"
    }

    private var doubleBackToExitPressedOnce = false

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private lateinit var content: FrameLayout
    private lateinit var drawer: DrawerLayout
    private lateinit var statusScrim: View
    private lateinit var navigationView: NavigationView
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var btmNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    private val TOP_LEVEL_DESTINATIONS = setOf(
        R.id.homeFragment,
        R.id.listFragment,
        R.id.leadersFragment,
        R.id.profileFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateForTheme(mainActivityViewModel.currentTheme)
        setContentView(R.layout.activity_main)
//        setupNavigation()
    }

}