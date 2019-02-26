package com.westsamoaconsult.labtests.network

import com.westsamoaconsult.labtests.network.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/posts")
    fun getPosts(): Deferred<Response<List<Post>>>
}