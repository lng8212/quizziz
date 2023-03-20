package com.longkd.quizizz.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.longkd.quizizz.databinding.ItemHomeQuizBinding
import com.longkd.quizizz.model.QuizListModel

class HomeQuizListAdapter(private val clickListener: (QuizListModel) -> Unit) :
    ListAdapter<QuizListModel, HomeQuizListAdapter.HomeQuizViewHolder>(DiffCallback) {

    inner class HomeQuizViewHolder(private var binding: ItemHomeQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quizListModel: QuizListModel) {
            binding.quizListModel = quizListModel
            binding.quizCard.setOnClickListener {
                clickListener.invoke(quizListModel)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeQuizViewHolder {
        val view = ItemHomeQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeQuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeQuizViewHolder, position: Int) {
        val quizListModel = getItem(position)
        holder.bind(quizListModel)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<QuizListModel>() {
        override fun areItemsTheSame(oldItem: QuizListModel, newItem: QuizListModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: QuizListModel, newItem: QuizListModel): Boolean {
            return oldItem.quiz_id == newItem.quiz_id
        }
    }

}