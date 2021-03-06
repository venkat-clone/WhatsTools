package com.android.whatstools.screen.status

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.whatstools.utlis.StatusItem
import com.android.whatstools.databinding.StatusItemBinding

class statusAdapter(context:Context,list:List<StatusItem>,viewModel: StatusListViewModel): RecyclerView.Adapter<statusAdapter.ViewHolder?>() {
    var  context:Context
    var list:List<StatusItem>
    var viewModel:StatusListViewModel
    init {
        this.context = context
        this.list = list
        this.viewModel = viewModel
    }

    class ViewHolder(binding: StatusItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: StatusItemBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): statusAdapter.ViewHolder {
        return  ViewHolder(StatusItemBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    override fun onBindViewHolder(holder: statusAdapter.ViewHolder, position: Int) {
        holder.binding.image.setImageBitmap(list[position].getBitmap())
        holder.binding.root.setOnClickListener{
            viewModel.status.postValue(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


