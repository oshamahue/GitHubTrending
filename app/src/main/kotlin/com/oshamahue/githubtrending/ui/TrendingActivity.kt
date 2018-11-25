package com.oshamahue.githubtrending.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.oshamahue.githubtrending.R
import com.oshamahue.githubtrending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.activity_trending_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingActivity : AppCompatActivity() {

    private val viewModel: TrendingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_list)
        viewModel.progressLiveData.observe(this, Observer { showProgress ->
            if (showProgress) {
                progressBar.visibility = View.VISIBLE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                progressBar.visibility = View.INVISIBLE
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        })
        viewModel.toastLiveData.observe(
            this,
            Observer { Snackbar.make(constraintLayout, it, Snackbar.LENGTH_LONG).show() })

    }
}
