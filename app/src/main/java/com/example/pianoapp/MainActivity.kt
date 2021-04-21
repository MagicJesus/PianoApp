package com.example.pianoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var instruments: Array<String>
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var selectedInstrument: String
    private lateinit var toggleTonesButton: ToggleButton
    private lateinit var toggleSustainButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.instrumentSpinner)
        toggleTonesButton = findViewById(R.id.showTonesToggleButton)
        toggleSustainButton = findViewById(R.id.sustainToggleButton)
        instruments = arrayOf("Grand Piano", "Organ", "Guitar", "Electric Piano")
        arrAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            instruments)

        spinner.adapter = arrAdapter
        spinner.prompt = "Select an instrument"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedInstrument = spinner.selectedItem.toString()
            }

            // to raczej do wywalenia
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    fun startPianoActivity(view: View) {
        var toggleButtonChecked = "True"
        var sustainButtonChecked = "True"

        if(!toggleTonesButton.isChecked) {
            toggleButtonChecked = "False"
            sustainButtonChecked = "False"
        }

        val intent = Intent(this, PianoActivity::class.java)
        intent.putExtra("instrumentName", selectedInstrument)
        intent.putExtra("showTones", toggleButtonChecked)
        intent.putExtra("toggleSustain", sustainButtonChecked)

        startActivity(intent)
    }


}