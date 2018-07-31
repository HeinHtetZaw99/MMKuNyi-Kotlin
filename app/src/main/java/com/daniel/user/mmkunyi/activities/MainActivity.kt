package com.daniel.user.mmkunyi.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.adapters.JobViewAdapter
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.mvp.presenters.MainPresenter
import com.daniel.user.mmkunyi.mvp.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {


    protected lateinit var mPresenter: MainPresenter
    lateinit var mAdapter: JobViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = ViewModelProviders.of(this)[MainPresenter::class.java]
        mPresenter.initPresenter(this)
        mAdapter = JobViewAdapter(this, mPresenter)
        mainRv.layoutManager = LinearLayoutManager(this)
        mainRv.adapter = mAdapter
        checkConnection()
        swipeLayout.setOnRefreshListener {
            object : SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    if (isOnline()) {
                        mainRv.removeAllViews()
                        refreshFeed()
                    } else
                        Snackbar.make(mainRv, "No Network Connections", Snackbar.LENGTH_INDEFINITE).show()
                }

            }
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun refreshFeed() {
        swipeLayout.isRefreshing = false
        mPresenter.OnRefreshScreen()
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
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun launchDetailsScreen(id: Int) {
        var intent = Intent(this, JobDeatilsActivity::class.java)
        startActivity(intent)
    }

    fun isOnline(): Boolean {
        var connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun checkConnection() {
        if (isOnline()) {
            mPresenter.getJobsLD().observe(this, Observer<List<MMKunyiResponse>> {
                displayData(it)


            })
        } else
            Snackbar.make(mainRv, "No Network Connections", Snackbar.LENGTH_INDEFINITE).show()

    }
}
