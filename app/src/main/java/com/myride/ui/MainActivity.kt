package com.myride.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.myride.R
import com.myride.model.EntryType
import com.myride.ui.chart.EntriesChart
import com.myride.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animateHeader()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        with(viewModel) {
            loadEntries()
            entries?.observe(this@MainActivity, Observer { entries ->
                bikeChart.entries = entries?.filter { entry -> entry.type == EntryType.BIKE.index }
                carChart.entries = entries?.filter { entry -> entry.type == EntryType.CAR.index }
            })
        }
    }

    private fun animateHeader() {
        val header = findViewById(R.id.header_text) as View
        val drawable = header.background as AnimatedVectorDrawable

        with(drawable) {
            registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    start()
                }
            })
            start()
        }
    }

    fun onNewClick(view: View) {
        AddDialog(
                this,
                { newEntry -> viewModel.add(newEntry) }
        ).show()
    }

    val bikeChart by lazy { findViewById(R.id.bike_chart) as EntriesChart }
    val carChart by lazy { findViewById(R.id.car_chart) as EntriesChart }
}
