package com.longkd.quizizz.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.longkd.quizizz.MainActivity
import com.longkd.quizizz.R
import com.longkd.quizizz.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    companion object {
        const val SIGN_IN_REQUEST_CODE = 1001
    }

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.startLoginBtn.setOnClickListener {
            launchSignInFlow()
        }
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == SIGN_IN_REQUEST_CODE) {
                val response = IdpResponse.fromResultIntent(it.data)
                if (it.resultCode == Activity.RESULT_OK) {
                    // User successfully signed in

                    binding.startFeedback.text = getString(R.string.logged_in)
                    enterMainActivity()
//
//                val currentUser = FirebaseAuth.getInstance().currentUser!!
//                Timber.d(
//                    "Successfully signed in user ${currentUser.displayName}!"
//                )
//
//                val firebaseFireStore = FirebaseFirestore.getInstance()
//
//                val userMap = HashMap<String, Any?>()
//                userMap["email"] = currentUser.email
//                userMap["image"] = currentUser.photoUrl
//                userMap["name"] = currentUser.displayName
//
//                firebaseFireStore.collection("Users").document(currentUser.uid).set(userMap)
//                    .addOnSuccessListener {
//                        Timber.d("Saved in Users ${currentUser.displayName}!")
//                    }.addOnFailureListener{
//                        Timber.d("$it")
//                    }
//
                } else {
                    Timber.d("Sign in unsuccessful ${response?.error?.errorCode}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            binding.startFeedback.text = getString(R.string.logged_in)

            enterMainActivity()
        }
    }


    private fun launchSignInFlow() {
        val providers = arrayListOf(
//            AuthUI.IdpConfig.AnonymousBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
//            AuthUI.IdpConfig.EmailBuilder().build()
        )

        // Create and launch sign-in intent.                                     1
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    private fun enterMainActivity() {
        finish()
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
        startActivity(Intent(this, MainActivity::class.java))
    }
}