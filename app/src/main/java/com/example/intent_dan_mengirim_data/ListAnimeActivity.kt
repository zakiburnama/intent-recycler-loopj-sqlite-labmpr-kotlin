package com.example.intent_dan_mengirim_data

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent_dan_mengirim_data.databinding.ActivityListAnimeBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ListAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListAnimeBinding
    private val listAnime = ArrayList<Anime>()

    private val list = ArrayList<Animeku>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data dari API
        getListAnime()

        // Data dari String.xml
//        list.addAll(listAnimeku)
//        showRecyclerList()
    }

    //
    private val listAnimeku: ArrayList<Animeku>
        get() {
            val dataName = resources.getStringArray(R.array.anime_name)
            val dataDescription = resources.getStringArray(R.array.anime_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listAnime = ArrayList<Animeku>()
            for (i in dataName.indices) {
                val anime = Animeku(dataName[i],dataDescription[i], dataPhoto.getResourceId(i, -1))
                listAnime.add(anime)
            }
            return listAnime
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration
                .ORIENTATION_LANDSCAPE) {
            binding.listAnime.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listAnime.layoutManager = LinearLayoutManager(this)
        }

        val adapter = AnimekuAdapter(list)
        binding.listAnime.adapter = adapter
        adapter.setOnItemClickCallback(object : AnimekuAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Animeku) {
                Toast.makeText(this@ListAnimeActivity, "Kamu memilih "
                        + data.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //

    private fun getListAnime() {
        val client = AsyncHttpClient()
        val url = "https://anime-facts-rest-api.herokuapp.com/api/v1"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray)
            {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val data = responseObject.getString("data")
                    val jsonArray = JSONArray(data)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        val id = jsonObject.getInt("anime_id")
                        val name = jsonObject.getString("anime_name")
                        val img = jsonObject.getString("anime_img")

                        listAnime.add(Anime(id, name, img))
                    }
                    showRecycler()
                } catch (e: Exception) {
                    Toast.makeText(this@ListAnimeActivity,
                        e.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>,
                                   responseBody: ByteArray, error: Throwable)
            {
                Toast.makeText(this@ListAnimeActivity, statusCode, Toast.LENGTH_SHORT).show()
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

        val adapter = AnimeAdapter(listAnime)
        binding.listAnime.adapter = adapter

        adapter.setOnItemClickCallback(object : AnimeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Anime) {
                val intent = Intent(this@ListAnimeActivity,
                    DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ANIME, data.name)
                intent.putExtra(DetailActivity.EXTRA_ANIME_IMG, data.img)
                startActivity(intent)
            }
        })
    }

}