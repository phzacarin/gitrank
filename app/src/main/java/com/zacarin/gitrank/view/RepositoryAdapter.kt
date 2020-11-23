package com.zacarin.gitrank.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zacarin.gitrank.BR
import com.zacarin.gitrank.R
import com.zacarin.gitrank.databinding.RepositoryEntryBinding
import com.zacarin.gitrank.model.Item
import kotlinx.android.synthetic.main.repository_entry.view.*
import kotlin.properties.Delegates

/**
 * [RecyclerView.Adapter] to add [Item] data to the [RecyclerView] list.
 */
class RepositoryAdapter: RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var itemList: List<Item> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepositoryEntryBinding.inflate(inflater, parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item: Item = itemList[position]

            holder.itemView.apply {
                tv_repo_name.text = item.name
                tv_author_name.text = item.owner.login
                tv_fork_qty.text = item.forksCount.toString()
                tv_star_qty.text = item.stargazersCount.toString()
                Picasso.get()
                    .load(item.owner.avatarUrl)
                    .fit().centerCrop()
                    .placeholder(R.mipmap.avatar_placeholder)
                    .error(R.mipmap.no_image_placeholder)
                    .into(iv_avatar)
            }
            holder.setupData(item)
        }
    }

    fun updateData(products: ArrayList<Item>?) {
        products?.let {
            itemList = it
        }
    }

    inner class RepositoryViewHolder(private val binding: RepositoryEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setupData(item: Item) {
            binding.setVariable(BR.item, item)
        }
    }
}
