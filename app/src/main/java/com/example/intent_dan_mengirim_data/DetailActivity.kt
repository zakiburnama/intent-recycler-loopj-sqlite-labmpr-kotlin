package com.example.intent_dan_mengirim_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.intent_dan_mengirim_data.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvGetText: TextView = findViewById(R.id.tv_text1)

        // Mengambil nilai yang dikirmkan dari MainActivity
        // Karena nilai berupa string maka .getStringExtra
        val getText = intent?.getStringExtra(EXTRA_TEXT)

        tvGetText.text = getText

        // Mengambil nilai yang dikirmkan dari MainActivity
        // Karena nilai yang dikirim berupa bolean maka .getBooleanExtra
        val getBool = intent?.getBooleanExtra(EXTRA_BOOL, false)

        if (getBool == true) {
            // Mengambil nilai yang dikirmkan dari MainActivity
            // Karena nilai yang dikirim berupa objek dengan data class menggunakan Parcelabel maka kodenya sbb
            val getCar = intent?.getParcelableExtra<Car>(EXTRA_CAR)
            val carSpek = "Car Specification:\n" +
                    "\nName: ${getCar?.name.toString()} " +
                    "\nBrand: ${getCar?.brand.toString()} " +
                    "\nType: ${getCar?.type.toString()} " +
                    "\nYear: ${getCar?.year.toString()} " +
                    "\nPrice: ${getCar?.price.toString()}"
            tvGetText.text = carSpek
        }

    }

    // Variabel dibawah yang digunakan pada MainActivity tadi untuk mengrimkan nilai kesini
    companion object {
        const val EXTRA_TEXT = "extra_text"
        const val EXTRA_BOOL = "extra_bool"
        const val EXTRA_CAR = "extra_car"
    }
}