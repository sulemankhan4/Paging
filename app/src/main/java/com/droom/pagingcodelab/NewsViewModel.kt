package com.droom.pagingcodelab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.droom.pagingcodelab.model.NewsModel

class NewsViewModel : ViewModel() {
    var itemPagedList: LiveData<PagedList<NewsModel?>>
    var liveDataSource: LiveData<PageKeyedDataSource<Int, NewsModel?>>

    init {
        val itemDataSourceFactory = NewsDataSourceFactory()
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(NewsDataSource.PAGE_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder<Int, NewsModel>(itemDataSourceFactory, config).build()
    }

}