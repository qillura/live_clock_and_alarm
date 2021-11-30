package com.example.clock

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_ADD
import android.util.Log
import java.util.*
import kotlin.collections.HashMap


class AlarmManager(context: Context) {

    private var speech: TextToSpeech? = null
    private var alarms: HashMap<Int, String> = HashMap()

    init {
        speech = TextToSpeech(
            context
        ) { speech?.language = Locale.CHINESE; }

        alarms[getTime(7, 15, 1)] = "麦麦吃早饭啦。"
        alarms[getTime(7, 30, 1)] = "麦麦记得吃药哦。"
        alarms[getTime(7, 40, 1)] = "麦麦要出门了。"

        for (weekday in 0..1) {
            alarms[getTime(19, 0, weekday)] = "麦麦去上厕所吧。"
            alarms[getTime(19, 15, weekday)] = "麦麦要刷牙咯。"
            alarms[getTime(19, 45, weekday)] = "上床聊天吧！"
            alarms[getTime(20, 0, weekday)] = "麦麦睡觉时间啦！"

            alarms[getTime(12, 0, weekday)] = "要不要吃个午饭？"
            alarms[getTime(18, 0, weekday)] = "准备吃香喷喷的晚饭了！好耶！"
        }
    }

    fun talk(hour: Int, min: Int, weekday: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        val tt: String? = getString(hour, min, weekday)
        if (tt != null) {
            for (i in 1..2) {
                speech?.speak(
                    tt,
                    QUEUE_ADD,
                    null,
                    tt
                )
            }
        }
    }

    private fun getString(hour: Int, min: Int, weekday: Int): String? {
        if (!shouldTalk(hour, min, weekday)) {
            return null
        }
        val tt: StringBuilder = StringBuilder("现在时间：")
        // 小时
        if (hour > 12) {
            tt.append("下午")
            tt.append(hour - 12)
        } else {
            tt.append(hour)
        }
        tt.append("点")
        // 分钟
        if (min > 0) {
            tt.append(" ")
            tt.append(min)
            tt.append("分。")
        } else {
            tt.append("整。")
        }
        tt.append("    ")

        if (alarms.containsKey(getTime(hour, min, weekday))) {
            tt.append(alarms[getTime(hour, min, weekday)])
            tt.append("    ")
        }

        return tt.toString();
    }

    private fun shouldTalk(hour: Int, min: Int, weekday: Int): Boolean {
        if (alarms.containsKey(getTime(hour, min, weekday))) {
            return true;
        }
        if ((min == 0 || min == 15 || min == 30 || min == 45)
            && (hour in 8..19)
        ) {
            return true
        }
        return false
    }


    private fun getTime(hour: Int, min: Int, weekday: Int): Int {
        return min + hour * 60 + weekday * 10000
    }
}
