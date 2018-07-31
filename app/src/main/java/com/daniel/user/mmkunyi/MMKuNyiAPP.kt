package com.daniel.user.mmkunyi

import android.app.Application
import com.daniel.user.mmkunyi.data.MMKuNyiModel

class MMKuNyiAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKuNyiModel.initModel(this)
    }

}