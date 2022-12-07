package com.example.intent_dan_mengirim_data

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    // Deklarasikan variabel global etKirimData dengan tipe EditText yang bakal menampung view EditText
    lateinit var etKirimData: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Deklarasikan variabel etKirimData tapi untuk dihubungkan dengan view dengan id et_kirim_data
        etKirimData = findViewById(R.id.et_kirim_data)

    }

    // Fungsi onClick untuk menangkap interaksi 'click' pada button yang telah dideklarasikan bahwa button tersebut menggunakan fungsi onlClick
    fun onClick(view: View) {
        // Percabangan karena banyak button yang bisa ditekan, maka dibuat percabanagan dengan parameter id
        // Jadi Perintah berjalan sesuai dengan id button yang ditekan
        when (view.id) {
            // Untuk melakukan Intent explicit biasa, memulai activity baru
            R.id.btn_intent_explicit -> {
                // Berikut cara deklarasikan Intent
                // Intent('ASAL', 'TUJUAN')
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // Mulai activity
                startActivity(intent)
            }

            // Melakukan intent dengan mengirimkan nilai ke Activity tujuan
            R.id.btn_intent_data -> {
                // Mengambil value pada edit text, lalu disimpan pada variabel text dalam bentuk string
                val text = etKirimData.text.toString()
                // Deklarasi Intent
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // Mengirimkan text yang disimpan tadi ke Activity tujuan dengan cara
                // intent.putExtra('TUJUAN'.'NAMA VARIABEL PENERIMA', 'NILAI YANG DIKRIM')
                intent.putExtra(DetailActivity.EXTRA_TEXT, text)
                // Mulai Activity
                startActivity(intent)
            }

            // Melalukan intent denganmengirimkan objek ke Activity tujuan
            R.id.btn_intent_objek -> {
                // Buat variabel untuk menampil nilai dengan tipe objek car (sudah dibuat data class-nya car.kt)
                val carSpek = Car(
                    "Civic",
                    "Honda",
                    "Sedan",
                    1997,
                    57000000.00
                )
                // Intent
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // Mengirimkan objek car, caranya sama saja dengan mengiriman nilai
                intent.putExtra(DetailActivity.EXTRA_CAR, carSpek)
                // Mengirimkan nilai true ke tujuan
                intent.putExtra(DetailActivity.EXTRA_BOOL, true)
                // Mulai Activity
                startActivity(intent)
            }

            // Contoh Intent Implicit
            R.id.btn_intent_implicit -> {
                val phoneNumber = "081234567890"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialPhoneIntent)
            }
        }
    }

}