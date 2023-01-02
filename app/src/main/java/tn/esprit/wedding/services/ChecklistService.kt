package tn.esprit.wedding.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.ChecklistResponse
import tn.esprit.wedding.models.ResponseChecklist
import tn.esprit.wedding.models.Wedding
import java.util.*
import kotlin.collections.ArrayList

interface ChecklistService {
    @GET("checklist/user/{id}")
    fun getAllChecklistByIdUser(
        @Path("id") id: String,
    ): Call<MutableList<Checklist>>

    @GET("checklist/{user_id}/{status}")
    fun getAllChecklistByStatus(
        @Path("user_id") user_id: String,
        @Path("status") status: String,
    ): Call<MutableList<Checklist>>

    @GET("checklist/{id}")
    fun getChecklistById(
        @Path("id") id : String,
    ): Call<ResponseChecklist>

    @Multipart
    @POST("checklist/user/{user_id}")
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
    @PUT("checklist/{idtask}")
    fun updateChecklist (
        @Path("idtask") idtask : String,
        @Part("nom") nom : RequestBody,
        @Part("type") type : RequestBody,
        @Part("note") note : RequestBody,
        @Part image : MultipartBody.Part?,
        @Part("date") date: Date,
        @Part("status") status: RequestBody,
    ):Call<String>

    @DELETE("checklist/{idtask}")
    fun deleteTaskById(
        @Path("idtask") idtask: String,
    ): Call<String>
}


