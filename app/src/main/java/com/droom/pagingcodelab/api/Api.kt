package com.droom.pagingcodelab.api

import com.droom.pagingcodelab.model.NewsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("newsReviewsAndVideoListdata/")
    fun getAnswers(
        @Query("rows_per_page") rowsPerPage: Int,
        @Query("page") page: Int,
        @Query("source") source: String,
        @Query("submit_hash") submitHash: String
    ): Call<NewsResponseModel>
}