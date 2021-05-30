package com.linh.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linh.myapplication.presentation.MainActivity
import com.linh.myapplication.presentation.admin.AdminActivity
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatActivity
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        val currentUser = auth.currentUser

        Timber.d("currentUser $currentUser")

        if (currentUser != null) {
            goToMain()
        } else {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("onRestart()")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Timber.d("onActivityResult() requestCode $requestCode")
        Timber.d("onActivityResult() resultCode $resultCode")
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Timber.d(user.toString())
                Timber.d(user?.email)

                goToMain()
            } else {
                Timber.e("Login fails with reason ${response?.error}")
            }
        }
    }

    private fun goToMain() {
        val user = FirebaseAuth.getInstance().currentUser

        val isAdmin = user?.getIdToken(false)?.result?.claims?.get("admin") as? Boolean ?: false

        val intent = if (!isAdmin) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, AdminActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val RC_SIGN_IN = 1
    }

}