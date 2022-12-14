package com.example.busschedule

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding
import java.util.*
// Calling constructor of ListAdapter of type <Schedule, ..ViewHolder> with the parameter DiffCallback fn
class BusStopAdapter(private val onClickItem: (Schedule) -> Unit): ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {
    // 1. Create ViewHolder to access the item got created in rv
    class BusStopViewHolder(private var binding: BusStopItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat", "NewApi")
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = SimpleDateFormat("h:mm a")
                .format(Date(schedule.arrivalTime.toLong() * 1000)
            )
        }
    }
    // 2. Inflate view and attach onClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        // Attaching on click in CreateView fn instead of BindView fn
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onClickItem(getItem(position))
            // without this, cannot access Schedule in FullScheduleFragment
            // This is more than a function. A custom data type.
            // Current item is fed into onClickItem: (Schedule) -> Unit
        }
        return viewHolder
    }
    // 3. DiffCallBack to find difference between old list and new list
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }

    // 4. Bind values
    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}