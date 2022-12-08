package com.example.intent_dan_mengirim_data

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent_dan_mengirim_data.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listCat = ArrayList<Cat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getListCat()
    }

    private fun getListCat() {
        val client = AsyncHttpClient()
        val url = "https://cataas.com/api/cats"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>,responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        val id = jsonObject.getString("_id")
                        val owner = jsonObject.getString("owner")

                        val tags = jsonObject.getJSONArray("tags")
                        var tag: String? = null
                        for (j in 0 until tags.length()) {
                            tag = if (tag == null)
                                tags[j].toString()
                            else
                                "$tag, " + tags[j].toString()
                        }

                        val img = "https://cataas.com/cat/$id"

                        listCat.add(Cat(owner, tag, img))
                    }

                    showRecycler()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity,e.message, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Toast.makeText(this@MainActivity, statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecycler() {
        if (applicationContext.resources.configuration.orientation == Configuration
                .ORIENTATION_LANDSCAPE) {
            binding.listCat.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listCat.layoutManager = LinearLayoutManager(this)
        }

        val adapter = CatAdapter(listCat)
        binding.listCat.adapter = adapter

        adapter.setOnItemClickCallback(object : CatAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Cat) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CAT, data.owner)
                intent.putExtra(DetailActivity.EXTRA_CAT_IMG, data.img)
                startActivity(intent)
            }
        })
    }

}