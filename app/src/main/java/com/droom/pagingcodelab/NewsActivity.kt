package com.droom.pagingcodelab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.droom.pagingcodelab.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by lazy {
        NewsViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = NewsAdapter(this)
        newsViewModel.responseList.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
        recyclerView?.adapter = adapter
    }
}