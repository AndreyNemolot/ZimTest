package com.example.zimtest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.zimtest.R
import com.example.zimtest.databinding.ActivityDetailBinding
import com.example.zimtest.getStringFromResources
import com.example.zimtest.model.Data


class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val data = intent.getParcelableExtra(getStringFromResources(R.string.data_key)) as Data
        binding.data = data
    }
}
