package com.daniel.user.mmkunyi.persistence.typeconverter

import android.arch.persistence.room.TypeConverter
import com.daniel.user.mmkunyi.data.vos.JobTagsVO
import com.daniel.user.mmkunyi.data.vos.LikeVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LikeTypeConverter{
    @TypeConverter
    fun toString(likeList: List<LikeVO>):String{
        return Gson().toJson(likeList)
    }

    @TypeConverter
    fun toJobTags(likeJson:String):List<LikeVO>{
        val listType=object : TypeToken<List<LikeVO>>(){}.type
        return Gson().fromJson(likeJson,listType)
    }
}