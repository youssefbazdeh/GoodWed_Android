package tn.esprit.wedding.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Wedding
import java.util.*

interface ChecklistService {
    @GET("checklist/{user_id}")
    fun getAllChecklistByIdUser(
        @Path("user_id") user_id: String,
    ): Call<MutableList<Checklist>>
    @Multipart
    @POST("checklist/{user_id}")
    fun addChecklist(
        @Part("nom") nom: RequestBody,
        @Part("type") type: RequestBody,
        @Part("note") note: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("date") date: Date,
        @Part("status") status: RequestBody,
        @Part("user_id") user_id: RequestBody,
    ): Call<Checklist>
    @Multipart
    @PUT("checklist/{_id}")
    fun updateChecklist (
        @Path("_id") _id : String,
        @Part("nom") nom : RequestBody,
        @Part("type") type : RequestBody,
        @Part("note") note : RequestBody,
        @Part image : MultipartBody.Part?,
        @Part("date") date: Date,
        @Part("status") status: RequestBody,
    ):Call<String>
}


