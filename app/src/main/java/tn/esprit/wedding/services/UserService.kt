package tn.esprit.wedding.services

import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.wedding.models.ResponseChecklist
import tn.esprit.wedding.models.ResponseUser
import tn.esprit.wedding.models.User
import tn.esprit.wedding.models.loginResponse
import java.util.Date

interface UserService {
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email") email:String,
              @Field("password") password:String
    ): Call<loginResponse>

    @GET("user/{id}")
    fun getUserById(
        @Path("id") id : String,
    ): Call<ResponseUser>


    @POST("user/signup")
    fun signUp(
        @Body user: User,
        ): Call<User>

    @PATCH("user/{user_id}")
    fun update(
        @Path("user_id") idUser : String,
       @Body user: User
    ): Call<User>
}