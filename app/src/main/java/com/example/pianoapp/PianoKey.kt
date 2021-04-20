package com.example.pianoapp

import android.graphics.Color
import android.widget.Button

// reprezentacja pojedynczego klawisza
class PianoKey(var button: Button, var streamID: Int, var isBlack: Boolean = false) {
    fun setUncliked() {
        if (isBlack) {
            button.setBackgroundColor(Color.BLACK)
        } else {
            button.setBackgroundColor(Color.WHITE)
        }
    }

    fun setNotVisible() {
        button.text = ""
    }
}