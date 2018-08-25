package com.daniel.user.mmkunyi.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import android.content.res.Resources
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.load.engine.Resource
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import com.daniel.user.mmkunyi.mvp.views.MainView
import com.google.android.gms.appinvite.AppInviteInvitation

open class MainPresenter : BasePresenter<MainView>(), JobItemDelegate {



    override fun onTapShare() {
        mView.sendInvitation()
    }


    private var mJobsLD: MutableLiveData<List<MMKunyiResponse>> = MutableLiveData()

    fun getJobsLD(): MutableLiveData<List<MMKunyiResponse>> {
        return mJobsLD
    }


    override fun onTabJobItem(id: String) {
        mView.launchDetailsScreen(id)
    }

    override fun onForceRefresh() {
    }

    override fun initPresenter(mView: MainView) {
        super.initPresenter(mView)
         MMKuNyiModel.getInstance().loadJobsFeed(mJobsLD, mErrorLD)
    }

    override fun onRefreshScreen() {
        MMKuNyiModel.getInstance().loadJobsFeed(mJobsLD, mErrorLD)

    }

    override fun onTapLike(id : Long) {
        mView.sendLikeToServer(id)
    }




}
