package com.pratamawijaya.firebaseauth.presentation.login

import com.google.firebase.auth.FirebaseUser

/**
 * Created by pratama on 8/31/17.
 */
interface LoginView {
    fun loginFailed(s: String)
    fun loginSuccess(user: FirebaseUser?)
}