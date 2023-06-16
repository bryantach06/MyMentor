package com.capstone.mymentor.data.retrofit

import com.capstone.mymentor.data.response.MentorResultResponse
import com.capstone.mymentor.data.response.MentorResultResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("user-responses/{docId}")
    fun getMentor(
        @Path("docId") docId: String
    ): Call<List<MentorResultResponseItem>>
}