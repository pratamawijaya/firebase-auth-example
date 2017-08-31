package com.pratamawijaya.firebaseauth.presentation.login

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*


/**
 * Created by pratama on 8/31/17.
 */
class LoginPresenter constructor(val view: LoginView) : FirebaseAuth.AuthStateListener, OnCompleteListener<AuthResult> {

    private val TAG = "Login"
    private var firebaseAuth = FirebaseAuth.getInstance()

    fun attachView() {
        firebaseAuth.addAuthStateListener(this)
    }

    fun detachView() {
        firebaseAuth.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        Log.d(TAG, "auth state changed")
        val user = firebaseAuth.currentUser

        user.let {
            view.loginSuccess(user)
        }
    }

    /**
     * get google credential
     */
    fun loginGoogle(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            firebaseLogin(credential)
        } else {
            view.loginFailed("login google fail")
        }
    }

    /**
     * handle credential
     */
    fun firebaseLogin(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this)

    }

    /**
     * listen task login is success or not
     */
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            val user = firebaseAuth.currentUser
            view.loginSuccess(user)
        } else {
            view.loginFailed(task.exception.toString())
        }
    }
}