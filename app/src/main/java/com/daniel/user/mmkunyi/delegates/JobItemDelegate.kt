package com.daniel.user.mmkunyi.delegates

interface JobItemDelegate {

    fun onTabJobItem(id: String)
    fun onForceRefresh()
    fun onRefreshScreen()
    fun onTapShare()
    fun onTapLike(id : Long)

}