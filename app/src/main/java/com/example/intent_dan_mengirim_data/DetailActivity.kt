package com.example.intent_dan_mengirim_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intent_dan_mengirim_data.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getCatImg = intent?.getStringExtra(EXTRA_CAT_IMG).toString()
        Picasso.get().load(getCatImg).into(binding.ivCat)

    }

    companion object {
        const val EXTRA_CAT = "extra_cat"
        const val EXTRA_CAT_IMG = "extra_cat_img"
    }
}