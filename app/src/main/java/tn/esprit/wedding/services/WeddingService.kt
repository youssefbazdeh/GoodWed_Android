package tn.esprit.wedding.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import tn.esprit.wedding.models.Wedding
import java.util.Date

interface WeddingService {
    @Multipart
    @POST("wedding")
    fun addWed(
        @Part("fullname") fullname: RequestBody,
        @Part("partner_fullname") partner_fullname: RequestBody,
        @Part("genre") genre: RequestBody,
        @Part("partner_sexe") partner_sexe: RequestBody,
        @Part("partner_email") partner_email: RequestBody,
        @Part("wedding_name") wedding_name: RequestBody,
        @Part("date_ceremonie") date_ceremonie: Date,
        @Part("heure_ceremonie") heure_ceremonie: Date,
        @Part("budget") budget: Int,
        @Part image: MultipartBody.Part
    ): Call<Wedding>
}