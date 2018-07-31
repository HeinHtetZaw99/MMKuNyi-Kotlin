package com.daniel.user.mmkunyi.mvp.presenters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.daniel.user.mmkunyi.mvp.views.BaseView

open abstract class BasePresenter<T : BaseView> : ViewModel() {
    protected lateinit var mView: T
    var mErrorLD: MutableLiveData<String> = MutableLiveData()
    fun getErrorLD(): MutableLiveData<String> {
        return mErrorLD
    }

    open fun initPresenter(mView: T) {
        this.mView = mView
    }

}