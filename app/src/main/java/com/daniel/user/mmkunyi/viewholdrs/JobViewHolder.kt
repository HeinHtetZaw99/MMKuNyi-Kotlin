package com.daniel.user.mmkunyi.viewholdrs

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import kotlinx.android.synthetic.main.view_item_job.view.*

class JobViewHolder(itemView: View,
                    private val mDelegate: JobItemDelegate) : BaseViewHolder<MMKunyiResponse>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun setData(data: MMKunyiResponse) {

        val jobTagList = data.jobTags
        val jobTag = jobTagList!![0]
        var tags: String = ""
        for (jobTagItem in jobTagList) {
            var tag = jobTagItem!!.tag
            tags = tags.plus("#$tag\n")

        }
        tags = tags.replace(" ", "_")


        Log.d("MMMMM", tags)
        val timeTakenInHours = (data.jobDuration!!.totalWorkingDays!! * data.jobDuration.workingHoursPerDay!!)

        itemView.jobTitle.text = jobTag!!.desc
        itemView.jobDescription.text = data.shortDesc

        itemView.jobTag.text = tags
        itemView.jobDurationInHours.text = timeTakenInHours.toString() + " hours"
        itemView.jobLocation.text = data.location
        itemView.totalWorkingDays.text = data.jobDuration.totalWorkingDays.toString() + " days"
    }

    override fun onClick(v: View?) {
        mDelegate.onTabJobItem(0)
    }

}