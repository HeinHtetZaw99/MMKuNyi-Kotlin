package com.daniel.user.mmkunyi.mvp.views

import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse

interface FormView : BaseView{
        fun postToFireBaseDB(data : MMKunyiResponse)
}