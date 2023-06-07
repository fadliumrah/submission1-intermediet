package com.fadli.aplikasistoryapp.ui.dashboard.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fadli.aplikasistoryapp.dataclass.Story
import com.fadli.aplikasistoryapp.utils.Constanta
import com.bumptech.glide.Glide
import com.fadli.aplikasistoryapp.databinding.RvStoryBinding
import com.fadli.aplikasistoryapp.ui.detail.DetailActivity


class HomeAdapter :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var data = mutableListOf<Story>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RvStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = data[position]
        holder.bind(story)
    }

    fun initData(story: List<Story>) {
        data.clear()
        data = story.toMutableList()
    }

    inner class ViewHolder(private val binding: RvStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(story: Story) {
            with(binding) {
                storyName.text = story.name

                Glide.with(itemView)
                    .load(story.photoUrl)
                    .into(storyImage)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(Constanta.StoryDetail.UserName.name, story.name)
                    intent.putExtra(Constanta.StoryDetail.ImageURL.name, story.photoUrl)
                    intent.putExtra(
                        Constanta.StoryDetail.ContentDescription.name,
                        story.description
                    )

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            /* transition between recyclerview & acitvity detail */
                            androidx.core.util.Pair(storyImage, "story_image"),
                            androidx.core.util.Pair(storyName, "user_name"),
                            androidx.core.util.Pair(defaultAvatar, "user_avatar"),
                            androidx.core.util.Pair(storyDescription, "story_description")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

}