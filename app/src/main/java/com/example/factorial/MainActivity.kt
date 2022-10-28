package com.example.factorial

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            binding.loadingProgressBar.visibility = View.INVISIBLE
            binding.calculateButton.isEnabled = true
            when (it) {
                is Progress -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.calculateButton.isEnabled = false
                }
                is Error -> {
                    Toast.makeText(this, "You is not enter value", Toast.LENGTH_SHORT).show()
                }
                is Factorial -> {
                    binding.factorial.text = it.factorial
                }
            }
        }
    }
}
