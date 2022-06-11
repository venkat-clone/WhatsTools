package com.android.whatstools.screen.Message

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.whatstools.MessageEntity
import com.android.whatstools.R

import com.android.whatstools.databinding.MessageItemBinding

class MessageAdapter(context:Context): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    var list :MutableList<MessageEntity> = ArrayList()
    var Lpos:Int =-1

    var context: Context
    init {
        Log.i("init","Adapter")
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        Log.i("onBindViewHolder","$list")
        val binding = MessageItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        Log.i("onBindViewHolder","$position")
        holder.binding.model = list[position]
        Animate(position,holder.binding.root)
    }

    fun Animate(pos:Int,view: View){
        if(Lpos>pos){
            view.animation = AnimationUtils.loadAnimation(context, R.anim.slide_to_bottom)
        }
        else{
            view.animation = AnimationUtils.loadAnimation(context, R.anim.slide_to_top)
        }
        Lpos =pos

    }

    fun upDate(list:List<MessageEntity>){
        this.list.addAll(list)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding:MessageItemBinding
        init {
            this.binding = binding
        }

    }
}
