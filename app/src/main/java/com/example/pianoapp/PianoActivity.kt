package com.example.pianoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PianoActivity : AppCompatActivity() {

    private lateinit var selectedInstrument: String
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_piano)

        // TODO: Set the right instrument sound based on selectedInstrument
        selectedInstrument = intent.getStringExtra("instrumentName")!!
    }
}