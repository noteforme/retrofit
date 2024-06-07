package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.theme.Contributor
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers


fun main() {
  PoemBookmarksReadViewModel().addArticle()
}

const val API_URL = "https://api.github.com"


class PoemBookmarksReadViewModel : ViewModel() {


  fun getArticle() {
    val call1 =
      Retrofit.Builder().baseUrl(jsonplaceURL).client(disableCertificateVerification()).build()
        .create(IApiStores::class.java).getArticle(2)
    call1?.enqueue(
      object : Callback<ResponseBody?> {
        override fun onResponse(
          call: Call<ResponseBody?>,
          response: Response<ResponseBody?>,
        ) {
          val body = response.body()?.string()

          Log.i("PoemBookmarksReadViewModel", "onResponse: $body")
        }

        override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
        }
      },
    )
  }


  fun addArticle() {

    val formBody = FormBody.Builder()
      .add("userId", "1")
      .add("title", "article 2")
      .add("body", "body article")
      .build()
    Retrofit.Builder().client(disableCertificateVerification()).baseUrl(jsonplaceURL).build()
      .create(IApiStores::class.java)
      .addArticle(formBody)
      ?.enqueue(
        object : Callback<ResponseBody?> {
          override fun onResponse(
            call: Call<ResponseBody?>,
            response: Response<ResponseBody?>,
          ) {
            val body = response.body()?.string()

            Log.i("PoemBookmarksReadViewModel", "onResponse: $body")
          }

          override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
          }
        },
      )
  }

  fun disableCertificateVerification(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(
      object : X509TrustManager {
        override fun checkClientTrusted(
          chain: Array<out java.security.cert.X509Certificate>?,
          authType: String?,
        ) {
        }

        override fun checkServerTrusted(
          chain: Array<out java.security.cert.X509Certificate>?,
          authType: String?,
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
          return arrayOf()
        }
      },
    )

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val sslSocketFactory = sslContext.socketFactory
    val trustAllHostnames = HostnameVerifier { _, _ -> true }

    return OkHttpClient.Builder()
      .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
      .hostnameVerifier(trustAllHostnames)
      .build()
  }


  fun rxJavaInvoke() {
    // Create a very simple REST adapter which points the GitHub API.
    val retrofit: Retrofit = Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build()

    // Create an instance of our GitHub API interface.
    val github = retrofit.create(IApiStores::class.java)

    // Create a call instance for looking up Retrofit contributors.
    github.contributorsOwner("square", "retrofit")
      .subscribeOn(Schedulers.io())
//      ?.observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        object : Subscriber<List<Contributor?>?>() {
          override fun onCompleted() {}
          override fun onError(e: Throwable) {
            println(e)
          }

          override fun onNext(contributors: List<Contributor?>?) {
            if (contributors != null) {
              for (contributor in contributors) {
                println(contributor?.login + " (" + contributor?.contributions + ")")
              }
            }
          }
        },
      )
  }

}


interface IApiStores {

  @GET("posts/{articleId}")
  fun getArticle(@retrofit2.http.Path("articleId") it: Int): Call<ResponseBody?>?


  @POST("posts")
  fun addArticle(@retrofit2.http.Body requestBody: RequestBody?): Call<ResponseBody?>?

  @GET("/repos/{owner}/{repo}/contributors")
  fun contributorsOwner(
    @Path("owner") owner: String?,
    @Path("repo") repo: String?,
  ): Observable<List<Contributor?>?>

}

val jsonplaceURL = "https://jsonplaceholder.typicoe.com/"
