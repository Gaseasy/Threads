package com.rakhimberdin.lab_2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.lang.Runnable
import kotlin.collections.ArrayList



class MainActivity : AppCompatActivity() {

    private var name: String = "0"
    var threads: ArrayList<Thread> = ArrayList()
    var times: ArrayList<Int> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendEt: EditText

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart = findViewById<Button>(R.id.button_start)
        val buttonStop = findViewById<Button>(R.id.button_stop)
        val buttonExit = findViewById<Button>(R.id.button_exit)
        val buttonSendAll = findViewById<Button>(R.id.button_send_all)
        sendEt = findViewById(R.id.et_send)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonStart.setOnClickListener {
            val thread = Thread(func(name.toInt()))
            name = (name.toInt() + 1).toString()
            thread.name = name
            thread.start()
            threads.add(thread)
            times.add(0)
            recyclerView.adapter = CustomRecyclerAdapter(threads, times)
        }

        buttonStop.setOnClickListener {
            if (threads.isNotEmpty()) {
                threads.last().interrupt()
                threads.remove(threads.last())
                recyclerView.adapter = CustomRecyclerAdapter(threads, times)
                Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
            }
            else
                finishAffinity()
        }

        buttonExit.setOnClickListener {
            finishAndRemoveTask()
        }

        buttonSendAll.setOnClickListener {
            val message = sendEt.text.toString()
            repeat(threads.size) {
                File(getFileStreamPath("common.txt"), "").appendText(message.plus("\n"))
            }
        }
    }

    private fun func(pos: Int): Runnable {
        val runnable = Runnable {
            kotlin.run {
                while (true) {
                    try {
                        Thread.sleep(1000)
                    }
                    catch (e: InterruptedException) {}
                    if (pos < threads.size) {
                        times[pos] += 1
                        println("Thread name: ${threads[pos].name} Execution time: ${times[pos]}")
                    }
                }
            }
        }

        return runnable
    }
}