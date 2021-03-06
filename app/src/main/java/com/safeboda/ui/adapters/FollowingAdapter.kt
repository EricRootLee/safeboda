/*
 * Copyright 2020 Safeboda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.safeboda.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safeboda.core.data.models.UserOrOrganization.Following
import com.safeboda.core.html.HtmlStyler
import com.safeboda.databinding.ListItemUserFollowingBinding
import com.safeboda.ui.interfaces.OnUserOrOrganizationSelectedListener

object FollowingDiffer : DiffUtil.ItemCallback<Following>() {

    override fun areItemsTheSame(oldItem: Following, newItem: Following) =
        oldItem.login == newItem.login

    override fun areContentsTheSame(oldItem: Following, newItem: Following) =
        oldItem == newItem
}

internal class FollowingAdapter(val onUserOrOrganizationSelectedListener: OnUserOrOrganizationSelectedListener) :
    ListAdapter<Following, FollowingAdapter.ViewHolder>(FollowingDiffer) {

    companion object {
        private const val ITEM_WIDTH_RATIO = 0.75f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemUserFollowingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(val binding: ListItemUserFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Following) {
            binding.user = item
            binding.following.setOnClickListener {
                onUserOrOrganizationSelectedListener.onUserOrOrgSelected(item.login)
            }
            binding.userName.text = item.name
            binding.userLogin.text = item.login
            HtmlStyler.styleText(binding.userBio, item.bioHtml, null)
            binding.widthRatio = ITEM_WIDTH_RATIO
            binding.executePendingBindings()
        }
    }
}