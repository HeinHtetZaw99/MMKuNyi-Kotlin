package com.daniel.user.mmkunyi.viewholdrs

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_item_job.view.*

abstract class BaseViewHolder<W>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    protected var mData: W? = null
    abstract fun setData(data: W)

    init {
        itemView.setOnClickListener(this)
    }
}
