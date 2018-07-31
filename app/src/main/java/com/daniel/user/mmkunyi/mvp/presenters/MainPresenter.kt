package com.daniel.user.mmkunyi.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.MmKuNyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import com.daniel.user.mmkunyi.mvp.views.MainView

open class MainPresenter : BasePresenter<MainView>(), JobItemDelegate {

    private var mJobsLD: MutableLiveData<List<MmKuNyiResponse>> = MutableLiveData()

    fun getJobsLD(): MutableLiveData<List<MmKuNyiResponse>> {
        return mJobsLD
    }


    override fun onTabJobItem(id: Int) {
        mView.launchDetailsScreen(id)
    }

    override fun onForceRefresh() {
    }

    override fun initPresenter(mView: MainView) {
        super.initPresenter(mView)
        MMKuNyiModel.getInstance().loadJobsFeed(mJobsLD, mErrorLD)
    }


}
