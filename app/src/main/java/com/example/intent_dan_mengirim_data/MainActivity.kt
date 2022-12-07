package com.example.intent_dan_mengirim_data

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent_dan_mengirim_data.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Animeku>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        list.addAll(listAnimeku)
        showRecyclerList()
    }


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
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.listAnime.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listAnime.layoutManager = LinearLayoutManager(this)
        }

        val adapter = AnimekuAdapter(list)
        binding.listAnime.adapter = adapter
        adapter.setOnItemClickCallback(object : AnimekuAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Animeku) {
                Toast.makeText(this@MainActivity, "Kamu memilih " + data.name, Toast.LENGTH_SHORT).show()
            }
        })
    }



}