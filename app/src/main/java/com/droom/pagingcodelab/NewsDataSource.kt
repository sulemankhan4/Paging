package com.droom.pagingcodelab


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.droom.pagingcodelab.api.RetrofitClient.Companion.instance
import com.droom.pagingcodelab.model.NewsModel

class NewsDataSource : PagingSource<Int, NewsModel>() {
    companion object {
        var PAGE_SIZE = 100
        const val ROWS_PER_PAGE = 10
        private const val FIRST_PAGE = 1
        private const val SITE_NAME = "from_discovery"
        const val SUBMIT_HASH = "f2768d9b3637c7be0bcb24f4a58c1618"
    }

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        val CURRENT_PAGE = params.key ?: 1
        try {
            val response =
                instance?.api?.getAnswers(ROWS_PER_PAGE, CURRENT_PAGE, SITE_NAME, SUBMIT_HASH)
            if (response != null) {

                PAGE_SIZE = response.data?.numPages ?: 0
                val hasMore = CURRENT_PAGE < PAGE_SIZE
                if (hasMore) {
                    return LoadResult.Page(
                        data = response.data?.newsList.orEmpty(),
                        prevKey = null,
                        nextKey = (params.key ?: 1) + 1
                    )
                }

            } else {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }


        } catch (e: Exception) {

        }
        return LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )
    }
}