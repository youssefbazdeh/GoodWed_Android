package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService
import tn.esprit.wedding.services.ChecklistService

class BudgetViewModel : ViewModel () {
    /***************** AFFICHAGE BY ID USER *****************/
    var budgetLiveData: MutableLiveData<MutableList<Budget>?> = MutableLiveData()
    val _budgetLiveData : LiveData<MutableList<Budget>?> = budgetLiveData

    fun getAllBudgetByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(BudgetService::class.java)
        val getBudget=retrofit.getAllBudgetByIdUser(user_id)
        getBudget.enqueue(object : Callback<MutableList<Budget>> {
            override fun onResponse(call: Call<MutableList<Budget>>, response: Response<MutableList<Budget>>) {
                if (response.isSuccessful){
                    budgetLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    budgetLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<Budget>>, t: Throwable) {
                budgetLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
    /***************** AFFICHAGE BY ID BUDGET *****************/
    var budgetLiveData1: MutableLiveData<Budget?> = MutableLiveData()
    val _budgetLiveData1 : LiveData<Budget?> = budgetLiveData1

    fun getBudgetById(id : String){
        val retrofit= ApiClient.getApiClient()!!.create(BudgetService::class.java)
        val getBudget=retrofit.getBudgetById(id)
        getBudget.enqueue(object : Callback<Budget> {
            override fun onResponse(call: Call<Budget>, response: Response<Budget>) {
                if (response.isSuccessful){
                    budgetLiveData1.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    budgetLiveData1.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<Budget>, t: Throwable) {
                budgetLiveData1.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })

    }





}