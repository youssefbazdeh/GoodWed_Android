package tn.esprit.wedding.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import tn.esprit.wedding.models.User
import tn.esprit.wedding.models.loginResponse

interface UserService {
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email") email:String,
              @Field("password") password:String
    ): Call<loginResponse>



    @POST("user/signup")
    fun signUp(
        @Body user: User,
        ): Call<User>
}