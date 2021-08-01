package com.udacity.project4.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.databinding.ActivityAuthenticationBinding

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        this.onSignInResult(result, RC_SIGN_IN)
    }


    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.login.setOnClickListener { launchSignInFlow() }

//         TODO: Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google

//          TODO: If the user was authenticated, send him to RemindersActivity

//          TODO: a bonus is to customize the sign in flow to look nice using :
        //https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#custom-layout

    }

    override fun onStart() {
        super.onStart()
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.i("yarab", "Logged Out")
            }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult, RC_SIGN_IN: Int) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(this, "this is the authorized user", Toast.LENGTH_LONG).show()
            Log.i("yarab", "$RC_SIGN_IN")
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Log.i("yarab", "${result.resultCode}")
            Log.i("yarab", "$RC_SIGN_IN")
            response?.error?.let {
                Toast.makeText(this, "hhhhh ${it.errorCode}", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun launchSignInFlow() {


        // provide ways for users to register and sign in.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }


    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
