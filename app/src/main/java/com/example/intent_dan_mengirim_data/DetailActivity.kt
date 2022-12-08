package com.example.intent_dan_mengirim_data

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent_dan_mengirim_data.anime.animeapi.DetailAnimeAdapter
import com.example.intent_dan_mengirim_data.databinding.ActivityDetailBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var getAnime: String
    private val listFacts = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val catImg = "https://cataas.com/cat/ZHrXPVRJniYPR6pp"

        Picasso.get().load(catImg).into(binding.ivAnime)

//        val tvGetText: TextView = findViewById(R.id.tv_text1)
//
//        val getText = intent?.getStringExtra(EXTRA_TEXT)
//        tvGetText.text = getText
//
//        val getBool = intent?.getBooleanExtra(EXTRA_BOOL, false)
//
//        if (getBool == true) {
//            val getCar = intent?.getParcelableExtra<Car>(EXTRA_CAR)
//            Log.i("TAG", getCar?.name.toString())
//            val carSpek = "Car Specification:\n" +
//                    "\nName: ${getCar?.name.toString()} " +
//                    "\nBrand: ${getCar?.brand.toString()} " +
//                    "\nType: ${getCar?.type.toString()} " +
//                    "\nYear: ${getCar?.year.toString()} " +
//                    "\nPrice: ${getCar?.price.toString()}"
//            tvGetText.text = carSpek
//        }


        // Untuk NETWORKING
//        getAnime = intent?.getStringExtra(EXTRA_ANIME).toString()

//        val getAnimeImg = intent?.getStringExtra(EXTRA_ANIME_IMG).toString()
//        Picasso.get().load(getAnimeImg).into(binding.ivAnime)

//        getListFacts()
    }

    private fun getListFacts() {
        val client = AsyncHttpClient()
        val url = "https://anime-facts-rest-api.herokuapp.com/api/v1/"+getAnime
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val data = responseObject.getString("data")
                    val jsonArray = JSONArray(data)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val fact = jsonObject.getString("fact")
                        listFacts.add(fact)
                    }
                    showRecycler()
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray, error: Throwable) {
                Toast.makeText(this@DetailActivity, statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecycler() {
        if (applicationContext.resources.configuration.orientation == Configuration
                .ORIENTATION_LANDSCAPE) {
            binding.listAnime.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listAnime.layoutManager = LinearLayoutManager(this)
        }

        val adapter = DetailAnimeAdapter(listFacts)
        binding.listAnime.adapter = adapter
    }


    companion object {
        const val EXTRA_TEXT = "extra_text"
        const val EXTRA_BOOL = "extra_bool"
        const val EXTRA_CAR = "extra_car"
        const val EXTRA_ANIME = "extra_anime"
        const val EXTRA_ANIME_IMG = "extra_anime_img"
    }
}