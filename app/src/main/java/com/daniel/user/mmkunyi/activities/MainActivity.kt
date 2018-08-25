package com.daniel.user.mmkunyi.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.adapters.JobViewAdapter
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.mvp.presenters.MainPresenter
import com.daniel.user.mmkunyi.mvp.views.MainView
import com.daniel.user.mmkunyi.utils.AppConstants
import com.google.android.gms.appinvite.AppInviteInvitation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {




    val RC_INVITE_TO_APP = 1237
    protected lateinit var mPresenter: MainPresenter
    lateinit var mAdapter: JobViewAdapter

    companion object {
        const val IE_NOTIFICATION_ID = "IE_NOTIFICATION_ID"
        const val IE_LAUNCH_ACTION = "IE_LAUNCH_ACTION"
        val LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION = 2222
        const val LAUNCH_ACTION_TAP_NOTI_BODY = 2223
        fun goToMain(from : Context) : Intent{
            return Intent(from , MainActivity::class.java)
        }
        fun newIntentSaveNews(context: Context, notificationId: Int): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(IE_NOTIFICATION_ID, notificationId)
            intent.putExtra(IE_LAUNCH_ACTION, LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION)
            return intent
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = ViewModelProviders.of(this)[MainPresenter::class.java]
        mPresenter.initPresenter(this)

        swipeLayout.isRefreshing = true

        mPresenter.getJobsLD().observe(this, Observer<List<MMKunyiResponse>> {
            if (isOnline()) {
                swipeLayout.isRefreshing = false
                displayData(it)


            } else
                Snackbar.make(mainRv, "No Network Connections", Snackbar.LENGTH_LONG).show()
        })

        mAdapter = JobViewAdapter(applicationContext, mPresenter)
        mainRv.layoutManager = LinearLayoutManager(this)
        mainRv.adapter = mAdapter

        swipeLayout.setOnRefreshListener {
            SwipeRefreshLayout.OnRefreshListener {
                if (isOnline()) {
                    refreshFeed()
                    swipeLayout.isRefreshing = false
                } else
                    Snackbar.make(mainRv, "No Network Connections", Snackbar.LENGTH_INDEFINITE).show()
            }
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.jobsTab



        postButton.setOnClickListener {
            Log.d(MMKuNyiModel.TAG, "fab pressed")

            val intent = Intent(this, ApplicationFormActivity::class.java)
            startActivity(intent)
        }


    }

    private fun refreshFeed() {
        mainRv.removeAllViews()
        swipeLayout.isRefreshing = false
        mPresenter.onRefreshScreen()
    }

    private fun displayData(data: List<MMKunyiResponse>?) {
        if (data != null) {
            mAdapter.appendNewData(data)
            Log.d("MM", "data size : " + data.size)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.jobsTab -> {
//                message.text = getString(R.string.title_job)
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
//                message.setText(R.string.title_profile)
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun launchDetailsScreen(id: String) {
        var intent = Intent(this, JobDeatilsActivity::class.java)
        intent.putExtra(AppConstants.JOB_ID, id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        navigation.selectedItemId = R.id.jobsTab
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun sendInvitation() {
        val intent = AppInviteInvitation.IntentBuilder("Join MM-News")
                .setMessage("Be informed on the news that matter to Myanmar people")
                .setCallToActionText("MMKuNyi")
                .build()
        startActivityForResult( intent, RC_INVITE_TO_APP)
    }

    override fun sendLikeToServer(id : Long) {
        MMKuNyiModel.getInstance().addLike(id)
    }



}
