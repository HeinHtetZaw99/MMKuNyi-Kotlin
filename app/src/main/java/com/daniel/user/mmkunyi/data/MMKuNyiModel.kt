package com.daniel.user.mmkunyi.data

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.daniel.user.mmkunyi.data.vos.ApplicantVO
import com.daniel.user.mmkunyi.data.vos.LikeVO
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.persistence.AppDatabase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class MMKuNyiModel(context: Context) {

    companion object {
        var fakeUserId = 1235678
        private var MM_KUNYI_FEED: String = "mmkunyi"
        var mDataBaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        const val TAG = "MMKUNYI"
        const val AUTH_CODE = "346405297083-7h2aufl69prhsvqf3k94l303irejl2j7.apps.googleusercontent.com"


        private var INSTANCE: MMKuNyiModel? = null
        fun getInstance(): MMKuNyiModel {
            if (INSTANCE == null) {
                throw RuntimeException("Model is being invoked before it was initialized ")
            }
            var i = INSTANCE
            return i!!
        }

        fun initModel(context: Context) {
            INSTANCE = MMKuNyiModel(context)
        }

    }

    var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    var mFirebaseUser: FirebaseUser? = mFirebaseAuth.currentUser

    private var mTheDB: AppDatabase = AppDatabase.getDatabase(context = context)

    fun loadJobsFeed(mJobsLD: MutableLiveData<List<MMKunyiResponse>>, mErrorLD: MutableLiveData<String>) {
        Log.d("MM", "Reached in loadJobsFeed")

        mDataBaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                mErrorLD.value = error!!.message
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val jobsList = ArrayList<MMKunyiResponse>()
                if (dataSnapshot != null) {

                    for (snapShot in dataSnapshot.children) {
                        val jobItem: MMKunyiResponse = snapShot.getValue<MMKunyiResponse>(MMKunyiResponse::class.java)!!
                        jobsList.add(jobItem)

                    }
                    saveToDB(jobsList)
                    mJobsLD.value = jobsList

                } else
                    mErrorLD.value = "API ERROR ... Please Try Again"
            }

        })

    }

    private fun saveToDB(jobsList: List<MMKunyiResponse>) {
        val ids: LongArray = mTheDB.jobListDao().insertAll(jobsList)
        Log.d("MM_Entry", ids.toString())
    }

    fun getJobById(jobId: Int): MMKunyiResponse {
        return mTheDB.jobListDao().getJobById(jobId)
    }

    fun isUserSignIn(): Boolean {
        return mFirebaseUser != null
    }

    fun authenticateUserWithGoogleAccount(signInAccount: GoogleSignInAccount, delegate: SignInWithGoogleAccountDelegate) {
        Log.d(MMKuNyiModel.TAG, "signInAccount Id :" + signInAccount.id!!)
        val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    Log.d(MMKuNyiModel.TAG, "signInWithCredential:onComplete:" + task.isSuccessful)
                    mFirebaseAuth = FirebaseAuth.getInstance()
                    mFirebaseUser = mFirebaseAuth!!.currentUser
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Log.d(MMKuNyiModel.TAG, "signInWithCredential", task.exception)
                        delegate.onFailureSignIn(task.exception!!.message!!)
                    } else {
                        Log.e(MMKuNyiModel.TAG, "signInWithCredential - successful")
                        delegate.onSuccessSignIn(signInAccount)

                    }
                }
                .addOnFailureListener { e ->
                    Log.e(MMKuNyiModel.TAG, "OnFailureListener : " + e.message)
                    delegate.onFailureSignIn(e.message!!)
                }
    }

    interface SignInWithGoogleAccountDelegate {
        fun onSuccessSignIn(signInAccount: GoogleSignInAccount)

        fun onFailureSignIn(msg: String)
    }

    fun getUserData(): Array<String?> {

        var userName = mFirebaseUser!!.displayName
        var userGmail = mFirebaseUser!!.email
        var userProfile = mFirebaseUser!!.photoUrl.toString()
        val userData: Array<String?> = arrayOf(userName, userGmail, userProfile)
        Log.d(MMKuNyiModel.TAG, userData.toString())
        return userData
    }

    fun addNewJob(data: MMKunyiResponse) {
        mDataBaseReference.child((data.jobPostId.toString())).setValue(data)
    }

    fun addNewApplicant(jobId: Int, data: ApplicantVO) {
        val responseData = mTheDB.jobListDao()
        val applicantList = responseData.getJobById(jobId).applicant
        var i = 0
        for (applicant in applicantList!!) {
            i++
        }

        mDataBaseReference.child(jobId.toString()).child("applicant").child(i.toString()).setValue(data)
    }


    fun addLike(newsId: Long) {
        val newLike = LikeVO.initLikeVo(fakeUserId, java.sql.Date(System.currentTimeMillis()).toString(), true)
        mDataBaseReference.child(newsId.toString()).child("likes").child(fakeUserId.toString()).setValue(newLike)

        //        mNewsFeedDR.child(String.valueOf(newsId)).child("likes")
        //                .child(String.valueOf(newLike.getLikeId())).removeValue();
    }

    fun logOut(context: Context) {
        try {
            if (mFirebaseUser != null)
                mFirebaseUser = null
            FirebaseAuth.getInstance().signOut()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

    }
}