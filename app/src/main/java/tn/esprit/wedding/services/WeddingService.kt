package tn.esprit.wedding.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.wedding.models.Wedding

interface WeddingService {
    @POST("wedding/addwed")
    fun addWed(
        @Body wedding: Wedding,
    ): Call<Wedding>
}