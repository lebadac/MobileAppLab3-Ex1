package com.dac.lab3_ex1.data.api

import com.dac.lab3_ex1.data.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
// Interface that defines methods to call the News API
interface NewsApiService {
    // Function to fetch a list of articles from the API
    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    companion object {
        private const val BASE_URL = "https://newsapi.org/"

        // Function to create a Retrofit instance to communicate with the API
        fun create(): NewsApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }
}
