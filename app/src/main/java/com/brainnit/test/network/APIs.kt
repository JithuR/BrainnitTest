package com.brainnit.test.network

import com.brainnit.test.model.ResponseModel
import com.brainnit.test.utils.MEDIA
import retrofit2.Call
import retrofit2.http.GET


interface APIs {

    /*
    *  Media call API
    */
    @GET(MEDIA)
    fun getMediaList(): Call<ResponseModel>
}