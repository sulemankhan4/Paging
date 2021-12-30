package com.droom.pagingcodelab

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.droom.pagingcodelab.model.NewsModel

class NewsDataSourceFactory() : DataSource.Factory<Int, NewsModel?>() {
    private val itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, NewsModel?>> =
        MutableLiveData<PageKeyedDataSource<Int, NewsModel?>>()

    override fun create(): DataSource<Int, NewsModel?> {
        val itemDataSource = NewsDataSource()
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }

    fun getItemLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, NewsModel?>> {
        return itemLiveDataSource
    }
}