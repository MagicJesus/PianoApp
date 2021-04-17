package com.example.pianoapp

import android.media.AudioAttributes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi


class PianoActivity : AppCompatActivity() {

    private lateinit var selectedInstrument: String
    private lateinit var text: TextView


    private lateinit  var soundPool: SoundPool
    private var notes = MutableList(24) {i -> 0}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piano)

        // TODO: Set the right instrument sound based on selectedInstrument
        selectedInstrument = intent.getStringExtra("instrumentName")!!

        // soundPool
        val attr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(24)
            .setAudioAttributes(attr)
            .build()



        if (selectedInstrument == "Guitar") {
            notes[0] = soundPool.load(baseContext, R.raw.a3, 1)
            notes[1] = soundPool.load(baseContext, R.raw.ais3, 1)
            notes[2] = soundPool.load(baseContext, R.raw.b3, 1)
            notes[3] = soundPool.load(baseContext, R.raw.c3, 1)
            notes[4] = soundPool.load(baseContext, R.raw.cis3, 1)
            notes[5] = soundPool.load(baseContext, R.raw.d3, 1)
            notes[6] = soundPool.load(baseContext, R.raw.dis3, 1)
            notes[7] = soundPool.load(baseContext, R.raw.e3, 1)
            notes[8] = soundPool.load(baseContext, R.raw.f3, 1)
            notes[9] = soundPool.load(baseContext, R.raw.fis3, 1)
            notes[10] = soundPool.load(baseContext, R.raw.g3, 1)
            notes[11] = soundPool.load(baseContext, R.raw.gis3, 1)
            notes[12] = soundPool.load(baseContext, R.raw.a4, 1)
            notes[13] = soundPool.load(baseContext, R.raw.ais4, 1)
            notes[14] = soundPool.load(baseContext, R.raw.b4, 1)
            notes[15] = soundPool.load(baseContext, R.raw.c4, 1)
            notes[16] = soundPool.load(baseContext, R.raw.cis4, 1)
            notes[17] = soundPool.load(baseContext, R.raw.d4, 1)
            notes[18] = soundPool.load(baseContext, R.raw.dis4, 1)
            notes[19] = soundPool.load(baseContext, R.raw.e4, 1)
            notes[20] = soundPool.load(baseContext, R.raw.f4, 1)
            notes[21] = soundPool.load(baseContext, R.raw.fis4, 1)
            notes[22] = soundPool.load(baseContext, R.raw.g4, 1)
            notes[23] = soundPool.load(baseContext, R.raw.gis4, 1)
        }

        // TODO
        if (selectedInstrument == "Piano") {

        }

        // TODO
        if (selectedInstrument == "Organ") {

        }
    }

    public fun play(v: View) {
        when (v.id) {
            R.id.a4 -> soundPool.play(notes[0], 1F, 1F, 0, 0, 1F)
            R.id.ais4 -> soundPool.play(notes[1], 1F, 1F, 0, 0, 1F)
            R.id.b4 -> soundPool.play(notes[2], 1F, 1F, 0, 0, 1F)
            R.id.c4 -> soundPool.play(notes[3], 1F, 1F, 0, 0, 1F)
            R.id.cis4 -> soundPool.play(notes[4], 1F, 1F, 0, 0, 1F)
            R.id.d4 -> soundPool.play(notes[5], 1F, 1F, 0, 0, 1F)
            R.id.dis4 -> soundPool.play(notes[6], 1F, 1F, 0, 0, 1F)
            R.id.e4 -> soundPool.play(notes[7], 1F, 1F, 0, 0, 1F)
            R.id.f4 -> soundPool.play(notes[8], 1F, 1F, 0, 0, 1F)
            R.id.fis4 -> soundPool.play(notes[9], 1F, 1F, 0, 0, 1F)
            R.id.g4 -> soundPool.play(notes[10], 1F, 1F, 0, 0, 1F)
            R.id.gis4 -> soundPool.play(notes[11], 1F, 1F, 0, 0, 1F)
            R.id.a5 -> soundPool.play(notes[12], 1F, 1F, 0, 0, 1F)
            R.id.ais5 -> soundPool.play(notes[13], 1F, 1F, 0, 0, 1F)
            R.id.b5 -> soundPool.play(notes[14], 1F, 1F, 0, 0, 1F)
            R.id.c5 -> soundPool.play(notes[15], 1F, 1F, 0, 0, 1F)
            R.id.cis5 -> soundPool.play(notes[16], 1F, 1F, 0, 0, 1F)
            R.id.d5 -> soundPool.play(notes[17], 1F, 1F, 0, 0, 1F)
            R.id.dis5 -> soundPool.play(notes[18], 1F, 1F, 0, 0, 1F)
            R.id.e5 -> soundPool.play(notes[19], 1F, 1F, 0, 0, 1F)
            R.id.f5 -> soundPool.play(notes[20], 1F, 1F, 0, 0, 1F)
            R.id.fis5 -> soundPool.play(notes[21], 1F, 1F, 0, 0, 1F)
            R.id.g5 -> soundPool.play(notes[22], 1F, 1F, 0, 0, 1F)
            R.id.gis5 -> soundPool.play(notes[23], 1F, 1F, 0, 0, 1F)
            else -> { // Note the block
                print("lol")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}