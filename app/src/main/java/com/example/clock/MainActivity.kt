package com.example.clock

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    var tv: TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.clock_time)
        setTime()

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                setTime()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    fun setTime() {
        val cal: Calendar = Calendar.getInstance()
        cal.time = Date()
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        var min: Int = cal.get(Calendar.MINUTE)
        var sec: Int = cal.get(Calendar.SECOND)
        tv!!.setText("" + hour + " : " + min + " : " + sec)
    }

}
