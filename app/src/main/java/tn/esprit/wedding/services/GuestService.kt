package tn.esprit.wedding.services

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Guest


interface GuestService {
    @GET("guest/user/{user_id}")
    fun getAllGuestByIdUser(
        @Path("user_id") user_id: String,
    ): Call<MutableList<Guest>>

    @GET("guest/{user_id}/{note}")
    fun getAllGuestByNote(
        @Path("user_id") user_id: String,
        @Path("note") note: String,
    ): Call<MutableList<Guest>>

    @GET("guest/{id}")
    fun getGuestById(
        @Path("id") id : String,
    ): Call<Guest>


    @POST("guest")
    fun addGuest(
    @Body guest: Guest
    ): Call<Guest>

    @PUT("guest/{idguest}")
    fun updateGuest (
        @Path("idguest") idguest : String,
        @Body guest: Guest
    ): Call<Guest>

    @DELETE("guest/{idguest}")
    fun deleteGuestById(
        @Path("idguest") idguest: String,
    ): Call<String>
}