package com.example.zimtest.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zimtest.R
import com.example.zimtest.databinding.ListItemBinding
import com.example.zimtest.model.Data

class RecyclerAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.DataHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: Data)
    }

    private var dataList:List<Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        val binding = ListItemBinding.bind(itemView)
        return DataHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val currentNote = dataList[position]
        holder.binding(currentNote, listener)
    }

    fun setList(itemList: List<Data>) {
        this.dataList = itemList
        notifyDataSetChanged()
    }

    inner class DataHolder(private var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(dataItem: Data, listener: OnItemClickListener?) {
            dataItem.index=(layoutPosition+1)
            binding.data =dataItem
            if (listener != null) {
                binding.root.setOnClickListener { _ -> listener.onItemClick(dataList[layoutPosition]) }
            }
            binding.executePendingBindings()
        }
    }
}