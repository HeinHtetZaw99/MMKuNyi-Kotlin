package com.daniel.user.mmkunyi.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.daniel.user.mmkunyi.viewholdrs.BaseViewHolder

open abstract class BaseRecyclerAdapter<T, W>(context: Context) : RecyclerView.Adapter<BaseViewHolder<W>>() {

    protected var mData: MutableList<W>? = null
    protected var mLayoutInflator: LayoutInflater

    val items: List<W>         //TODO :: asked back to teacher
        get() {
            val data = mData
            return data ?: ArrayList()
        }

    init {
        mData = ArrayList()
        mLayoutInflator = LayoutInflater.from(context)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<W>, position: Int) {
        holder.setData(mData!![position])
    }


    override fun getItemCount(): Int {
        return mData!!.size
    }


    fun appendNewData(newData: List<W>) {
        mData!!.addAll(newData)
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): W? {
        return if (position < mData!!.size - 1) mData!![position] else null
    }

    fun clearData() {
        mData = ArrayList()
        notifyDataSetChanged()
    }


}