package com.rakhimberdin.lab_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.*


class CustomRecyclerAdapter(private val threads: ArrayList<Thread>,
                            private val times: ArrayList<Int>):
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemInfo: TextView = itemView.findViewById(R.id.item_tv)
        val buttonSend: Button = itemView.findViewById(R.id.button_send_curr)
        val sendEt: EditText = itemView.findViewById(R.id.et_send_curr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemInfo.text = "Thread name: ${threads[position].name} Execution time: ${times[position]}"
        holder.buttonSend.setOnClickListener {
            val message = holder.sendEt.text.toString()
            File(it.context.getFileStreamPath(threads[position].name.plus(".txt")), "").writeText(message.plus("\n"))
            Toast.makeText(it.context, "Data delivered!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return threads.size
    }
}