package com.daniel.user.mmkunyi.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.daniel.user.mmkunyi.MMKuNyiAPP
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.mvp.presenters.LoginPresenter
import com.daniel.user.mmkunyi.mvp.views.LoginView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_main.*

class LogInActivity : BaseActivity() , LoginView, GoogleApiClient.OnConnectionFailedListener {



    protected val RC_GOOGLE_SIGN_IN = 11911
    lateinit var mPresenter : LoginPresenter
    lateinit var mGoogleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mPresenter = ViewModelProviders.of(this)[LoginPresenter::class.java]
        mPresenter.initPresenter(this)

        if(MMKuNyiModel.getInstance().mFirebaseUser != null){
            goToMain()
        }


        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(MMKuNyiModel.AUTH_CODE)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOption)
                .build()

        googleSignInButton.setOnClickListener {
            if(isOnline()) {
                    mPresenter.onPressLoginButton()

            }else{
                Toast.makeText(this,"Check Your Network Connection ", Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
     }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        }
    }

    private fun processGoogleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            // Google Sign-In was successful, authenticate with Firebase
            val account = result.signInAccount
            MMKuNyiModel.getInstance().authenticateUserWithGoogleAccount(account!!, object : MMKuNyiModel.SignInWithGoogleAccountDelegate {
                override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                    mPresenter.onUserSignedIn()
                    Log.d(MMKuNyiModel.TAG , "Login succeeded")
                }

                override fun onFailureSignIn(msg: String) {
                }
            })
        } else {
            // Google Sign-In failed
            Log.e(MMKuNyiModel.TAG, "Google Sign-In failed.")
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun goToMain() {
        val intent = MainActivity.goToMain(this)
        startActivity(intent)
        finish()
    }


    companion object {
        fun goToLogin(from : Context) : Intent{
            val intent = Intent(from , LogInActivity::class.java)
            return intent
        }


    }

}
