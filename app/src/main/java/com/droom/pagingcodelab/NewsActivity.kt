package com.droom.pagingcodelab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.droom.pagingcodelab.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    /***
     *
     *  Some features of Paging 3.
     *  1) Paging 3 library is completely rewritten in Kotlin with coroutines.
     *  2) In memory caching of data, ensures efficient use of system resources.
     *  3) Prevents api request duplication.
     *  4) Built in support for error handling, refresh and retry functionality.
     *  5) Built in support for loading state headers, footers and list separators.
     *
     *
     *  Steps to migrate to paging 2 to paging 3 ( Refer migration drawable )
     *  1) Data Source -> In data source implement, PagingSource instead of PageKeyedDataSource which will remove loadInitial, loadBefore, load After methods and will add getRefreshKey and load method.
     *      We need to do api call in load method
     *      This method handles load initial, load before and load after of paging 2.
     *      after api call, once we have successfull result from api.
     *      we need to do
     *
     *      return LoadResult.Page(
     *      data = response.data?.newsList.orEmpty(),
     *      prevKey = null,
     *      nextKey = (params.key ?: 1) + 1)
     *
     *      where data is list to be displayed in recycler view.
     *      prev key is key for pagination of prev page.
     *      next key is key for pagination of next page.
     *      params.key indicates page no of current page being called.
     *      when we dont have any data i.e we have reached at end of list, we can use
     *
     *      return LoadResult.Page(
     *      data = emptyList(),
     *      prevKey = null,
     *      nextKey = null)
     *
     *      In pagination, suppose we came across a exception, we can use
     *      return LoadResult.Error(e) in catch block of exception handling.
     *
     *  2) In Paging 3, there is no need of data source factory, so data source factory is removed in paging 3.
     *
     *  3) In Our View Model, in Paging 3, we need to define paging size and link it with data source.
     *      In Paging 3, paged list is replaced with pager.
     *      So in paging 3, we can define.
     *      val responseList = Pager(PagingConfig(NewsDataSource.PAGE_SIZE)) {
     *          NewsDataSource()
     *      }.liveData.cachedIn(viewModelScope)
     *
     *      .liveData - indicates we are converting our api response from news data source into live data so it can be observed into activity / fragment.
     *      .cachedIn - we are caching response by viewModelScope .
     *
     *  4) observe data changes in activity/fragment and update adapter.
     *
     *  5) In Adapter, PagedListAdapter (Paging 2.0 ) will be converted to PagingDataAdapter ( Paging 3.0 )
     */


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