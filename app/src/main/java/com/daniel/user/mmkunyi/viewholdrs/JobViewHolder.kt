package com.daniel.user.mmkunyi.viewholdrs

import android.content.res.Resources
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat.getColor
import android.util.Log
import android.view.View
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import kotlinx.android.synthetic.main.view_item_job.view.*

class JobViewHolder(itemView: View,
                    private val mDelegate: JobItemDelegate) : BaseViewHolder<MMKunyiResponse>(itemView) {
    private lateinit var jobList: MMKunyiResponse
    override fun setData(data: MMKunyiResponse) {
        jobList = data
        var likeNo = 0
        val jobTagList = data.jobTags
        val jobTag = jobTagList!![0]
        var tags: String = ""
        for (jobTagItem in jobTagList) {
            var tag = jobTagItem!!.tag
            tags = tags.plus("#$tag\n")

        }
        tags = tags.replace(" ", "_")


        Log.d("MMMMM", tags)
        val timeTakenInHours = (data.jobDuration!!.totalWorkingDays!! * data.jobDuration!!.workingHoursPerDay!!)

        itemView.jobTitle.text = jobTag!!.desc
        itemView.jobDescription.text = data.shortDesc
        val likeList = jobList.like

        for (like in likeList!!){
            if (like.liked == true){
                likeNo++
            }
        }
        itemView.likeButton.text = likeNo.toString()
        itemView.jobTag.text = tags
        itemView.jobDurationInHours.text = timeTakenInHours.toString() + " hours"
        itemView.jobLocation.text = data.location
        itemView.totalWorkingDays.text = data.jobDuration!!.totalWorkingDays.toString() + " days"
        if (data.isActive)
            itemView.jobState.setBackgroundColor( ContextCompat.getColor(itemView.context ,R.color.colorGreen) )
        else
            itemView.jobState.setBackgroundColor(ContextCompat.getColor(itemView.context , R.color.colorRed))

        itemView.shareButton.setOnClickListener {
            mDelegate.onTapShare()
        }

        itemView.likeButton.setOnClickListener {
            mDelegate.onTapLike(jobList.jobPostId!!.toLong())

        }


    }

    override fun onClick(v: View?) {
        mDelegate.onTabJobItem(jobList!!.jobPostId.toString()!!)
    }



}