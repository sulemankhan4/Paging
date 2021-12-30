package com.droom.pagingcodelab.model

data class NewsResponseModel(
    var code: String? = "",
    var data: NewsDataModel? = null
)


data class NewsDataModel(
    var newsList: List<NewsModel> = ArrayList(),
    var numPages: Int? = 0
)

data class NewsModel(
    var _id: String? = "",
    var heading: String? = "",
    var alternate_heading: String? = "",
    var images: String? = ""
)