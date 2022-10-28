package com.example.factorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.calculateButton.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            if (it.isInProgress) {
                binding.loadingProgressBar.visibility = View.VISIBLE
                binding.calculateButton.isEnabled = false
            } else {
                binding.loadingProgressBar.visibility = View.INVISIBLE
                binding.calculateButton.isEnabled = true
            }

            if (it.isError) {
                Toast.makeText(this, "You is not enter value", Toast.LENGTH_SHORT).show()
            }
            binding.factorial.text = it.factorial
        }
    }
}
