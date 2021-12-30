package com.droom.pagingcodelab.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.droom.pagingcodelab.base.BaseApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient
        private get() = OkHttpClient().newBuilder().addInterceptor(
            ChuckerInterceptor(BaseApplication.getContext())
        ).build()
    val api: Api
        get() = retrofit.create(Api::class.java)

    companion object {
        private const val BASE_URL = "https://apinew.droom.in/v6/"
        private var mInstance: RetrofitClient? = null

        val instance: RetrofitClient?
            get() {
                if (mInstance == null) {
                    mInstance = RetrofitClient()
                }
                return mInstance
            }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}