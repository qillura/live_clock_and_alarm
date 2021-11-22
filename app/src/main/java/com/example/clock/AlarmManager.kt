package com.example.clock

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_ADD
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.util.Log
import java.util.*
import kotlin.collections.HashMap


class AlarmManager(context: Context) {

    private var speech: TextToSpeech? = null
    private var alarms: HashMap<Int, String> = HashMap()

    init {
        speech = TextToSpeech(
            context
        ) { speech?.language = Locale.SIMPLIFIED_CHINESE; }
        alarms[getTime(19, 45, 0)] = "上床聊天时间到了"
    }

    fun talk(hour: Int, min: Int, sec: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        if (sec == 0
            && (min == 0 || min == 15 || min == 30 || min == 45)
            && (hour in 8..19)
        ) {
            speech?.speak(
                "现在时间，" + hour + "点" + min + "分",
                QUEUE_ADD,
                null,
                "1"
            )
        }

        if (alarms.containsKey(getTime(hour, min, sec))) {
            for (i in 1..3) {
                speech?.speak(
                    alarms[getTime(hour, min, sec)],
                    QUEUE_ADD,
                    null,
                    "1"
                )
            }
        }
    }

    private fun getTime(hour: Int, min: Int, sec: Int): Int {
        return sec + min * 60 + hour * 36001
    }
}
