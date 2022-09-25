package com.example.intent_dan_mengirim_data

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var etKirimData: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etKirimData = findViewById(R.id.et_kirim_data)

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_intent_explicit -> {
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                val intent = Intent(this@MainActivity, ListAnimeActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_intent_data -> {
//                val text = etKirimData.text.toString()
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_TEXT, text)
                val intent = Intent(this@MainActivity, HomeworkActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_intent_objek -> {
                val carSpek = Car(
                    "Civic",
                    "Honda",
                    "Sedan",
                    1997,
                    57000000.00
                )
                Log.i("TAG", carSpek.toString())
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CAR, carSpek)
                intent.putExtra(DetailActivity.EXTRA_BOOL, true)
                startActivity(intent)
            }

            R.id.btn_intent_implicit -> {
                val phoneNumber = "081234567890"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }
        }
    }

}