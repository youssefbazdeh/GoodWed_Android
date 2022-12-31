package tn.esprit.wedding.services

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Guest

interface BudgetService {
    @GET("budget/user/{user_id}")
    fun getAllBudgetByIdUser(
        @Path("user_id") user_id: String,
    ): Call<MutableList<Budget>>

    @GET("budget/{id}")
    fun getBudgetById(
        @Path("id") id : String,
    ): Call<Budget>

    @POST("budget")
    fun addBudget(
       @Body budget: Budget
    ): Call<Budget>

    @PUT("budget/{idbudget}")
    fun updateBudget (
        @Path("idbudget") idbudget : String,
        @Body budget: Budget
    ): Call<Budget>

    @DELETE("budget/{idbudget}")
    fun deleteBudgetById(
        @Path("idbudget") idbudget: String,
    ): Call<String>
}