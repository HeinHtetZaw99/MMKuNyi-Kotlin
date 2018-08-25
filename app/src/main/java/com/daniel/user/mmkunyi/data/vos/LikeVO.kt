package com.daniel.user.mmkunyi.data.vos

data class LikeVO(var seekerID: Int? = null,
                  var likeTimeStamp: String? = null
                  , var liked: Boolean? = null) {
    companion object {
        fun initLikeVo(id: Int, timeStamp: String, isLiked: Boolean): LikeVO {
            val data = LikeVO()
            data.seekerID = id
            data.likeTimeStamp = timeStamp
            data.liked = isLiked
            return data
        }
    }
}