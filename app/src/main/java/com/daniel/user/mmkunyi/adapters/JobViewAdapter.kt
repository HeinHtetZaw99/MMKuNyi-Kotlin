package com.daniel.user.mmkunyi.adapters

import android.content.Context
import android.view.ViewGroup
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.delegates.JobItemDelegate
import com.daniel.user.mmkunyi.viewholdrs.JobViewHolder

class JobViewAdapter(context: Context,
                     private val mJobItemDelegate: JobItemDelegate) : BaseRecyclerAdapter<JobViewHolder, MMKunyiResponse>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val jobItemView = mLayoutInflator.inflate(R.layout.view_item_job, parent, false)
        return JobViewHolder(jobItemView, mJobItemDelegate)
    }


}