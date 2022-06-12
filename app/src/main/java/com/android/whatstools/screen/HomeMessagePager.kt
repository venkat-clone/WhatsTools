package com.android.whatstools.screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.whatstools.MessageEntity
import com.android.whatstools.databinding.HomeMessageItemBinding

class HomeMessagePager(list:List<MessageEntity>,context: Context):PagerAdapter() {

    val MsgList:List<MessageEntity>
    val context:Context
    init {
        this.MsgList = list
        this.context = context
    }

    override fun getCount(): Int {
        return MsgList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: HomeMessageItemBinding = HomeMessageItemBinding.inflate(LayoutInflater.from(context))
        binding.message = MsgList[position]
        container.addView(binding.root)

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}