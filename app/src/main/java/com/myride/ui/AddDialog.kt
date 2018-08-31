package com.myride.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.myride.R
import com.myride.model.EntryType
import com.myride.repository.entities.EntryTable

class AddDialog(context: Context, val callback: (entry: EntryTable) -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add)

        initType()
        initScore()
        initButton()
    }

    private fun initButton() {
        addButton.setOnClickListener {
            callback(createEntry())
            dismiss()
        }
    }

    private fun createEntry(): EntryTable {
        return EntryTable(
                type = typeSpinner.selectedItemPosition + 1,
                score = scoreSpinner.selectedItemPosition + 1
        )
    }

    private fun initType() {
        val typeAdapter = ArrayAdapter<String>(context, R.layout.adapter_spinner)
        with(typeAdapter) {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            addAll(EntryType.BIKE.name, EntryType.CAR.name)
            typeSpinner.adapter = this
        }
    }

    private fun initScore() {
        val scoreAdapter = ArrayAdapter<String>(context, R.layout.adapter_spinner)
        with(scoreAdapter) {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            addAll((1..10).map { it.toString() })
            scoreSpinner.adapter = this
        }
    }

    val typeSpinner by lazy {  findViewById(R.id.type_spinner) as Spinner }
    val scoreSpinner by lazy {  findViewById(R.id.score_spinner) as Spinner }
    val addButton by lazy {  findViewById(R.id.add_button) as Button }
}