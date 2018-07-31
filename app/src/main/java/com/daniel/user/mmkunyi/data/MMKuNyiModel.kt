package com.daniel.user.mmkunyi.data

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.google.firebase.database.*

class MMKuNyiModel private constructor(
        private var mDataBaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
//        private var mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
//        private var mFirebaseUser: FirebaseUser = mFirebaseAuth.currentUser!!
) {
    private lateinit var mJobsFeedDR: DatabaseReference

    companion object {
        private var MM_KUNYI_FEED: String = "mmkunyi"

        private var INSTANCE: MMKuNyiModel? = null
        fun getInstance(): MMKuNyiModel {
            if (INSTANCE == null) {
                throw RuntimeException("Model is being invoked before it was initialized ")
            }
            var i = INSTANCE
            return i!!
        }

        fun initModel(context: Context) {
            INSTANCE = MMKuNyiModel()
        }

    }

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
                    mJobsLD.value = jobsList

                } else
                    mErrorLD.value = "API ERROR ... Please Try Again"
            }

        })

    }

}