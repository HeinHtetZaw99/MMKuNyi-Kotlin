package com.daniel.user.mmkunyi.viewholdrs

import android.view.View
import com.daniel.user.mmkunyi.data.vos.MmKuNyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import kotlinx.android.synthetic.main.view_item_job.view.*

class JobViewHolder(itemView: View,
                    private val mDelegate: JobItemDelegate) : BaseViewHolder<MmKuNyiResponse>(itemView) {
    override fun setData(data: MmKuNyiResponse) {

        val jobTagList = data.jobTags
        val jobTag = jobTagList!![0]
        var tagString: String = ""
        for (tag in jobTagList) {
            var tags = "#" + tag.tag + "/n"
            tagString.plus(tags)
            tagString.replace("", "_")
        }
        val timeTakenInHours = data.jobDuration!!.totalWorkingDays * data.jobDuration!!.workingHoursPerDay

        itemView.jobTitle.text = jobTag.desc
        itemView.jobDescription.text = data.shortDesc

        itemView.jobTag.text = tagString
        itemView.jobDurationInHours.text = timeTakenInHours.toString()
        itemView.jobLocation.text = data.location
        itemView.totalWorkingDays.text = data.jobDuration!!.totalWorkingDays.toString()

    }

    override fun onClick(v: View?) {
        mDelegate.onTabJobItem(0)
    }

}