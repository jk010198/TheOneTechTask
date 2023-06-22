package com.technologies.theone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technologies.theone.databinding.UserRowBinding
import com.technologies.theone.model.User

class UserAdapter(
    private val list: List<User>,
    val clickListener: (User) -> Unit,
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(UserRowBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            val pos = list[position]
            user = pos

            cardView.setOnClickListener({
                clickListener(list[position])
            })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}