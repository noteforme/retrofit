package com.example.myapplication.coroutine.api

import com.example.myapplication.coroutine.bean.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): ArticleResponse

}
