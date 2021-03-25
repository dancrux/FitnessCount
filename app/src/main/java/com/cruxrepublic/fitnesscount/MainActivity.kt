package com.cruxrepublic.fitnesscount

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    var running = false
    var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        handleButtonClicks()
    }

    private fun handleButtonClicks() {
        startButton.setOnClickListener {
            running = true
            var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if (stepSensor == null){
                Toast.makeText(this, "No Step Counter Sensor", Toast.LENGTH_SHORT).show()
            }else{
                sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            }
        }
        stopButton.setOnClickListener {
            running = false
            sensorManager?.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running){
            textView2.text = event?.values?.get(0).toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}