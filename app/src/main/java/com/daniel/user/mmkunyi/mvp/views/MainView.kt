package com.daniel.user.mmkunyi.mvp.views

interface MainView : BaseView {
    fun launchDetailsScreen(id: String)
    fun sendInvitation()
    fun sendLikeToServer(id :Long)
}