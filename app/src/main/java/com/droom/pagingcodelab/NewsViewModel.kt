package com.droom.pagingcodelab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData

class NewsViewModel : ViewModel() {

    val responseList = Pager(PagingConfig(NewsDataSource.PAGE_SIZE)) {
        NewsDataSource()
    }.liveData.cachedIn(viewModelScope)


}