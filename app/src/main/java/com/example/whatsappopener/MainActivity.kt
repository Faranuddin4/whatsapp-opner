package com.example.whatsappopener

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing number as 0
        var number: String = "0"

        // checking if intent is ACTION_PROCESS_TEXT, if yes then convert the text into string and store it in number
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        }

        // checking if number is digits only then call startWhatsapp() otherwise show toast
        if (number.isDigitsOnly()) {
            startWhatsapp(number)
        } else {
            Toast.makeText(this, "Please check the number!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startWhatsapp(number: String) {
        // making object of intent class as ACTION_VIEW
        val intent = Intent(Intent.ACTION_VIEW)
        // creating package of whatsapp
        intent.setPackage("com.whatsapp")
        // checking the format of number and modifying accordingly
        val data: String = if (number[0] == '+') {
            number.substring(1)
        } else if (number.length == 11) {
            "92$number"
        } else {
            number
        }
        // uri parsing the url
        intent.data = Uri.parse("https://wa.me/$data")
        // checking if user have installed whatsapp and then starting activity otherwise show toast
        if (packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please install whatsapp!", Toast.LENGTH_SHORT).show()
        }
        finish() // to finish the activity
    }
}
