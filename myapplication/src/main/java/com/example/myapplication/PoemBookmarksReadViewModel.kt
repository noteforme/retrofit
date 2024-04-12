package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST

class PoemBookmarksReadViewModel : ViewModel() {

  fun getArticle() {
    Retrofit.Builder().baseUrl(jsonplaceURL).build().create(IApiStores::class.java).getArticle(2)
      ?.enqueue(
          object : Callback<ResponseBody?> {
              override fun onResponse(
                  call: Call<ResponseBody?>,
                  response: Response<ResponseBody?>
              ) {
                Log.i("PoemBookmarksReadViewModel", "onResponse: ${response.body()}")
              }

              override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
              }
          },
      )


//    RetrofitFactory.create(
//      IApiStores::class.java
//    ).getArticle(2).enqueue(
//      object : Callback<ResponseBody> {
//        override fun onResponse(
//          call: Call<ResponseBody>,
//          response: Response<ResponseBody>,
//        ) {
//          Log.i(TAG, "onResponse: ${response.body()}")
//        }
//
//        override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {}
//      })
  }

}


interface IApiStores {

  @GET("posts/{articleId}")
  fun getArticle(@retrofit2.http.Path("articleId") it: Int): retrofit2.Call<ResponseBody?>?


  @POST("posts")
  fun addArticle(@retrofit2.http.Body requestBody: RequestBody?): retrofit2.Call<ResponseBody?>?
}

val jsonplaceURL = "https://jsonplaceholder.typicode.com/"
