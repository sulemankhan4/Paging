package com.droom.pagingcodelab


import androidx.paging.PageKeyedDataSource
import com.droom.pagingcodelab.api.RetrofitClient.Companion.instance
import com.droom.pagingcodelab.model.NewsModel
import com.droom.pagingcodelab.model.NewsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDataSource : PageKeyedDataSource<Int, NewsModel?>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsModel?>
    ) {
        instance
            ?.api
            ?.getAnswers(
                rowsPerPage = ROWS_PER_PAGE,
                page = FIRST_PAGE,
                source = SITE_NAME,
                submitHash = SUBMIT_HASH
            )
            ?.enqueue(object : Callback<NewsResponseModel?> {
                override fun onResponse(
                    call: Call<NewsResponseModel?>,
                    response: Response<NewsResponseModel?>
                ) {
                    if (response.body() != null) {
                        PAGE_SIZE = response.body()?.data?.numPages ?: 0
                        val listToUse = response.body()?.data?.newsList ?: ArrayList()
                        callback.onResult(listToUse, null, FIRST_PAGE + 1)
                    }
                }

                override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {}
            })
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel?>) {
        instance?.api
            ?.getAnswers(
                rowsPerPage = ROWS_PER_PAGE,
                page = params.key,
                source = SITE_NAME,
                submitHash = SUBMIT_HASH
            )
            ?.enqueue(object : Callback<NewsResponseModel?> {
                override fun onResponse(
                    call: Call<NewsResponseModel?>,
                    response: Response<NewsResponseModel?>
                ) {
                    if (response.body() != null) {
                        val key = if (params.key > 1) params.key - 1 else null
                        val listToUse = response.body()?.data?.newsList ?: ArrayList()

                        callback.onResult(listToUse, key)
                    }
                }

                override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {}
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel?>) {
        val currentPage = params.key
        instance
            ?.api
            ?.getAnswers(
                rowsPerPage = ROWS_PER_PAGE,
                page = currentPage,
                source = SITE_NAME,
                submitHash = SUBMIT_HASH
            )
            ?.enqueue(object : Callback<NewsResponseModel?> {
                override fun onResponse(
                    call: Call<NewsResponseModel?>,
                    response: Response<NewsResponseModel?>
                ) {
                    if (response.body() != null) {
                        val key = if (currentPage < PAGE_SIZE) params.key + 1 else null
                        val listToUse = response.body()?.data?.newsList ?: ArrayList()
                        callback.onResult(listToUse, key)
                    }
                }

                override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {}
            })
    }

    companion object {
        var PAGE_SIZE = 100
        const val ROWS_PER_PAGE = 10
        private const val FIRST_PAGE = 1
        private const val SITE_NAME = "from_discovery"
        const val SUBMIT_HASH = "f2768d9b3637c7be0bcb24f4a58c1618"
    }
}