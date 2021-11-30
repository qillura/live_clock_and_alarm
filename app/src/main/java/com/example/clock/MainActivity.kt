package com.example.clock

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    var tv: TextView? = null;
    var am: AlarmManager? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()

        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.clock_time)
        am = AlarmManager(this)

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                setTime()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
//
//        val installIntent = Intent()
//        installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
//        startActivity(installIntent)

//        val testRunnable = Runnable { test() }
//        handler.postDelayed(testRunnable, 2000)
    }

    private fun test() {
//        am?.talk(7, 45, 1)
//        am?.talk(7, 40, 1)
//        am?.talk(7, 55, 0)
//        am?.talk(12, 0, 0)
//        am?.talk(13, 0, 0)
        am?.talk(7, 40, 0)
        am?.talk(19, 0, 1)
    }

    fun setTime() {
        val cal: Calendar = Calendar.getInstance()
        cal.time = Date()

        val sb = StringBuilder()
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        sb.append(hour)

        sb.append(" : ")
        val min: Int = cal.get(Calendar.MINUTE)
        if (min < 10) {
            sb.append("0")
        }
        sb.append(min)

        sb.append(" : ")
        val sec: Int = cal.get(Calendar.SECOND)
        if (sec < 10) {
            sb.append("0")
        }
        sb.append(sec)

        tv!!.text = sb

        // talking part
        if (sec != 0) {
            return
        }

        var weekDay = cal.get(Calendar.DAY_OF_WEEK)
        weekDay = if (weekDay in (2..6)) {
            1
        } else {
            0
        }
        am?.talk(hour, min, weekDay)
    }

}
