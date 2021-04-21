package com.example.pianoapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.AudioAttributes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.media.SoundPool
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import kotlin.concurrent.thread

class PianoActivity : AppCompatActivity() {

    private lateinit var selectedInstrument: String
    private lateinit  var soundPool: SoundPool

    // tablica klawiszy i ich streamID nadanych po kliknięciu
    private lateinit var keysArray: Array<PianoKey>

    // lista plików .wav (zależna od wybranego instrumentu)
    private var sounds = MutableList(24) {i -> 0}

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piano)

        // atrybuty dla SoundPool
        val attr = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

        // obiekt do obslugi i odtwarzania dźwięków
        soundPool = SoundPool.Builder()
                .setMaxStreams(24)
                .setAudioAttributes(attr)
                .build()

        selectedInstrument = intent.getStringExtra("instrumentName")!!
        val showTones = intent.getStringExtra("showTones")!!.toBoolean()
        var toggleSustain = intent.getStringExtra("toggleSustain")!!.toBoolean()
        loadSounds() // załadowanie plików .wav

        keysArray = arrayOf(
                PianoKey(findViewById(R.id.c4), 0), PianoKey(findViewById(R.id.cis4), 0, true),
                PianoKey(findViewById(R.id.d4), 0), PianoKey(findViewById(R.id.dis4), 0, true),
                PianoKey(findViewById(R.id.e4), 0), PianoKey(findViewById(R.id.f4), 0),
                PianoKey(findViewById(R.id.fis4), 0, true), PianoKey(findViewById(R.id.g4), 0),
                PianoKey(findViewById(R.id.gis4), 0, true), PianoKey(findViewById(R.id.a4), 0),
                PianoKey(findViewById(R.id.ais4), 0, true), PianoKey(findViewById(R.id.b4), 0),
                PianoKey(findViewById(R.id.c5), 0), PianoKey(findViewById(R.id.cis5), 0, true),
                PianoKey(findViewById(R.id.d5), 0), PianoKey(findViewById(R.id.dis5), 0, true),
                PianoKey(findViewById(R.id.e5), 0), PianoKey(findViewById(R.id.f5), 0),
                PianoKey(findViewById(R.id.fis5), 0, true), PianoKey(findViewById(R.id.g5), 0),
                PianoKey(findViewById(R.id.gis5), 0, true), PianoKey(findViewById(R.id.a5), 0),
                PianoKey(findViewById(R.id.ais5), 0, true), PianoKey(findViewById(R.id.b5), 0))

        // wyłącz podpisy klawiszów
        if (!showTones) {
            keysArray.forEach { it -> it.setNotVisible()}
        }

        // dla każdego przycisku listener obsł↓gujący wciskanie, przytrzymanie i puszczanie klawisza
        keysArray.forEachIndexed {index, it -> it.button.setOnTouchListener { v, event ->
            handleTouch(event.action, index, toggleSustain)
            true }
        }
    }

    // zamiast android:onClick="play"
    private fun handleTouch(action: Int, keyNumber: Int, sustain: Boolean) {
        when (action) {
            // wciśnięcie klawisza
            MotionEvent.ACTION_DOWN -> {
                keysArray[keyNumber].streamID = soundPool.play(sounds[keyNumber],
                        1F, 1F, 0, 0, 1F)
                keysArray[keyNumber].button.setBackgroundColor(Color.GRAY)
            }

            // puszczenie klawisza
            MotionEvent.ACTION_UP -> {
                keysArray[keyNumber].setUncliked()
                if (! sustain){
                thread {
                    dampSound(keysArray[keyNumber].streamID)
                }
                }
            }
        }
    }

    // stopniowo stłum dźwięk po zdjęciu palca z klawisza
    private fun dampSound(streamID: Int) {
        soundPool.setVolume(streamID, 0.9f, 0.9f)
        Thread.sleep(100)
        soundPool.setVolume(streamID, 0.8f, 0.8f)
        Thread.sleep(100)
        soundPool.setVolume(streamID, 0.6f, 0.6f)
        Thread.sleep(100)
        soundPool.setVolume(streamID, 0.4f, 0.4f)
        Thread.sleep(100)
        soundPool.setVolume(streamID, 0.1f, 0.1f)
        Thread.sleep(100)

        soundPool.stop(streamID)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun loadSounds() {
        when (selectedInstrument) {
            "Guitar" -> {
                sounds[0] = soundPool.load(baseContext, R.raw.c4_guitar, 1)
                sounds[1] = soundPool.load(baseContext, R.raw.cis4_guitar, 1)
                sounds[2] = soundPool.load(baseContext, R.raw.d4_guitar, 1)
                sounds[3] = soundPool.load(baseContext, R.raw.dis4_guitar, 1)
                sounds[4] = soundPool.load(baseContext, R.raw.e4_guitar, 1)
                sounds[5] = soundPool.load(baseContext, R.raw.f4_guitar, 1)
                sounds[6] = soundPool.load(baseContext, R.raw.fis4_guitar, 1)
                sounds[7] = soundPool.load(baseContext, R.raw.g4_guitar, 1)
                sounds[8] = soundPool.load(baseContext, R.raw.gis4_guitar, 1)
                sounds[9] = soundPool.load(baseContext, R.raw.a4_guitar, 1)
                sounds[10] = soundPool.load(baseContext, R.raw.ais4_guitar, 1)
                sounds[11] = soundPool.load(baseContext, R.raw.b4_guitar, 1)
                sounds[12] = soundPool.load(baseContext, R.raw.c5_guitar, 1)
                sounds[13] = soundPool.load(baseContext, R.raw.cis5_guitar, 1)
                sounds[14] = soundPool.load(baseContext, R.raw.d5_guitar, 1)
                sounds[15] = soundPool.load(baseContext, R.raw.dis5_guitar, 1)
                sounds[16] = soundPool.load(baseContext, R.raw.e5_guitar, 1)
                sounds[17] = soundPool.load(baseContext, R.raw.f5_guitar, 1)
                sounds[18] = soundPool.load(baseContext, R.raw.fis5_guitar, 1)
                sounds[19] = soundPool.load(baseContext, R.raw.g5_guitar, 1)
                sounds[20] = soundPool.load(baseContext, R.raw.gis5_guitar, 1)
                sounds[21] = soundPool.load(baseContext, R.raw.a5_guitar, 1)
                sounds[22] = soundPool.load(baseContext, R.raw.ais5_guitar, 1)
                sounds[23] = soundPool.load(baseContext, R.raw.b5_guitar, 1)
            }

            "Grand Piano" -> {
                sounds[0] = soundPool.load(baseContext, R.raw.c4_piano, 1)
                sounds[1] = soundPool.load(baseContext, R.raw.cis4_piano, 1)
                sounds[2] = soundPool.load(baseContext, R.raw.d4_piano, 1)
                sounds[3] = soundPool.load(baseContext, R.raw.dis4_piano, 1)
                sounds[4] = soundPool.load(baseContext, R.raw.e4_piano, 1)
                sounds[5] = soundPool.load(baseContext, R.raw.f4_piano, 1)
                sounds[6] = soundPool.load(baseContext, R.raw.fis4_piano, 1)
                sounds[7] = soundPool.load(baseContext, R.raw.g4_piano, 1)
                sounds[8] = soundPool.load(baseContext, R.raw.gis4_piano, 1)
                sounds[9] = soundPool.load(baseContext, R.raw.a4_piano, 1)
                sounds[10] = soundPool.load(baseContext, R.raw.ais4_piano, 1)
                sounds[11] = soundPool.load(baseContext, R.raw.b4_piano, 1)
                sounds[12] = soundPool.load(baseContext, R.raw.c5_piano, 1)
                sounds[13] = soundPool.load(baseContext, R.raw.cis5_piano, 1)
                sounds[14] = soundPool.load(baseContext, R.raw.d5_piano, 1)
                sounds[15] = soundPool.load(baseContext, R.raw.dis5_piano, 1)
                sounds[16] = soundPool.load(baseContext, R.raw.e5_piano, 1)
                sounds[17] = soundPool.load(baseContext, R.raw.f5_piano, 1)
                sounds[18] = soundPool.load(baseContext, R.raw.fis5_piano, 1)
                sounds[19] = soundPool.load(baseContext, R.raw.g5_piano, 1)
                sounds[20] = soundPool.load(baseContext, R.raw.gis5_piano, 1)
                sounds[21] = soundPool.load(baseContext, R.raw.a5_piano, 1)
                sounds[22] = soundPool.load(baseContext, R.raw.ais5_piano, 1)
                sounds[23] = soundPool.load(baseContext, R.raw.b5_piano, 1)
            }

            "Electric Piano" -> {
                sounds[0] = soundPool.load(baseContext, R.raw.c4_elec, 1)
                sounds[1] = soundPool.load(baseContext, R.raw.cis4_elec, 1)
                sounds[2] = soundPool.load(baseContext, R.raw.d4_elec, 1)
                sounds[3] = soundPool.load(baseContext, R.raw.dis4_elec, 1)
                sounds[4] = soundPool.load(baseContext, R.raw.e4_elec, 1)
                sounds[5] = soundPool.load(baseContext, R.raw.f4_elec, 1)
                sounds[6] = soundPool.load(baseContext, R.raw.fis4_elec, 1)
                sounds[7] = soundPool.load(baseContext, R.raw.g4_elec, 1)
                sounds[8] = soundPool.load(baseContext, R.raw.gis4_elec, 1)
                sounds[9] = soundPool.load(baseContext, R.raw.a4_elec, 1)
                sounds[10] = soundPool.load(baseContext, R.raw.ais4_elec, 1)
                sounds[11] = soundPool.load(baseContext, R.raw.b4_elec, 1)
                sounds[12] = soundPool.load(baseContext, R.raw.c5_elec, 1)
                sounds[13] = soundPool.load(baseContext, R.raw.cis5_elec, 1)
                sounds[14] = soundPool.load(baseContext, R.raw.d5_elec, 1)
                sounds[15] = soundPool.load(baseContext, R.raw.dis5_elec, 1)
                sounds[16] = soundPool.load(baseContext, R.raw.e5_elec, 1)
                sounds[17] = soundPool.load(baseContext, R.raw.f5_elec, 1)
                sounds[18] = soundPool.load(baseContext, R.raw.fis5_elec, 1)
                sounds[19] = soundPool.load(baseContext, R.raw.g5_elec, 1)
                sounds[20] = soundPool.load(baseContext, R.raw.gis5_elec, 1)
                sounds[21] = soundPool.load(baseContext, R.raw.a5_elec, 1)
                sounds[22] = soundPool.load(baseContext, R.raw.ais5_elec, 1)
                sounds[23] = soundPool.load(baseContext, R.raw.b5_elec, 1)
            }

            "Organ" -> {
                sounds[0] = soundPool.load(baseContext, R.raw.c4_organ, 1)
                sounds[1] = soundPool.load(baseContext, R.raw.cis4_organ, 1)
                sounds[2] = soundPool.load(baseContext, R.raw.d4_organ, 1)
                sounds[3] = soundPool.load(baseContext, R.raw.dis4_organ, 1)
                sounds[4] = soundPool.load(baseContext, R.raw.e4_organ, 1)
                sounds[5] = soundPool.load(baseContext, R.raw.f4_organ, 1)
                sounds[6] = soundPool.load(baseContext, R.raw.fis4_organ, 1)
                sounds[7] = soundPool.load(baseContext, R.raw.g4_organ, 1)
                sounds[8] = soundPool.load(baseContext, R.raw.gis4_organ, 1)
                sounds[9] = soundPool.load(baseContext, R.raw.a4_organ, 1)
                sounds[10] = soundPool.load(baseContext, R.raw.ais4_organ, 1)
                sounds[11] = soundPool.load(baseContext, R.raw.b4_organ, 1)
                sounds[12] = soundPool.load(baseContext, R.raw.c5_organ, 1)
                sounds[13] = soundPool.load(baseContext, R.raw.cis5_organ, 1)
                sounds[14] = soundPool.load(baseContext, R.raw.d5_organ, 1)
                sounds[15] = soundPool.load(baseContext, R.raw.dis5_organ, 1)
                sounds[16] = soundPool.load(baseContext, R.raw.e5_organ, 1)
                sounds[17] = soundPool.load(baseContext, R.raw.f5_organ, 1)
                sounds[18] = soundPool.load(baseContext, R.raw.fis5_organ, 1)
                sounds[19] = soundPool.load(baseContext, R.raw.g5_organ, 1)
                sounds[20] = soundPool.load(baseContext, R.raw.gis5_organ, 1)
                sounds[21] = soundPool.load(baseContext, R.raw.a5_organ, 1)
                sounds[22] = soundPool.load(baseContext, R.raw.ais5_organ, 1)
                sounds[23] = soundPool.load(baseContext, R.raw.b5_organ, 1)
            }
        }
    }
}
