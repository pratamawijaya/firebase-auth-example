package com.pratamawijaya.firebaseauth.presentation.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pratamawijaya.firebaseauth.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, LoginView {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var presenter: LoginPresenter
    private lateinit var googleSignInOption: GoogleSignInOptions
    private lateinit var googleApi: GoogleApiClient

    private val RC_GOOGLE_SIGNIN: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupGoogleApi()
        presenter = LoginPresenter(this)

        btnFacebook.setOnClickListener { }

        btnGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApi)
            startActivityForResult(intent, RC_GOOGLE_SIGNIN)
        }

        btnPhone.setOnClickListener { }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_GOOGLE_SIGNIN -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                presenter.loginGoogle(result)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    private fun setupGoogleApi() {
        googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleApi = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOption)
                .build()

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e("error", "error ${connectionResult.errorMessage}")
    }

    override fun loginFailed(s: String) {
        // todo handle login failed
        Toast.makeText(this,"login fail $s",Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess(user: FirebaseUser?) {
        // todo
        Toast.makeText(this, "hello ${user?.displayName}",Toast.LENGTH_SHORT).show()
    }
}
