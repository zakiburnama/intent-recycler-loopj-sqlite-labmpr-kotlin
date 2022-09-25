package com.example.intent_dan_mengirim_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent_dan_mengirim_data.databinding.ActivityAddHomeworkBinding
import com.example.intent_dan_mengirim_data.databinding.ActivityHomeworkBinding
import com.example.intent_dan_mengirim_data.db.HomeworkHelper
import com.example.intent_dan_mengirim_data.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeworkBinding
    private lateinit var adapter: HomeworkAdapter

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            // Akan dipanggil jika request codenya ADD
            when (result.resultCode) {
                AddHomeworkActivity.RESULT_ADD -> {
                    val note =
                        result.data?.getParcelableExtra<Homework>(AddHomeworkActivity.EXTRA_NOTE) as Homework
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddHomeworkActivity.RESULT_UPDATE -> {
                    val note =
                        result.data?.getParcelableExtra<Homework>(AddHomeworkActivity.EXTRA_NOTE) as Homework
                    val position =
                        result?.data?.getIntExtra(AddHomeworkActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                AddHomeworkActivity.RESULT_DELETE -> {
                    val position =
                        result?.data?.getIntExtra(AddHomeworkActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Homework"

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        adapter = HomeworkAdapter(object : HomeworkAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Homework?, position: Int?) {
                val intent = Intent(this@HomeworkActivity, AddHomeworkActivity::class.java)
                intent.putExtra(AddHomeworkActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(AddHomeworkActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvNotes.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddHomeworkActivity::class.java)
            resultLauncher.launch(intent)
        }

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Homework>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
//            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = HomeworkHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            binding.progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }


    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}