package com.daniel.user.mmkunyi.mvp.presenters

import com.daniel.user.mmkunyi.mvp.views.LoginView

class LoginPresenter : BasePresenter<LoginView>() {
    fun onPressLoginButton(){
        mView.signInWithGoogle()
    }
    fun onUserSignedIn(){
        mView.goToMain()

    }
}