package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService


class UpdateBudgetViewModel : ViewModel() {
    var updateBudgetLiveData : MutableLiveData<Budget> = MutableLiveData()
    var _updateBudgetLiveData : LiveData<Budget> = updateBudgetLiveData


    fun updateBudget(
        _id:String,
        budget: Budget
    ){
        val retrofit = ApiClient.getApiClient()!!.create(BudgetService::class.java)
        val update = retrofit.updateBudget(_id, budget)
        update.enqueue(object : Callback<Budget> {
            override fun onResponse(call: Call<Budget>, response: Response<Budget>){
                if (response.isSuccessful){
                    updateBudgetLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    updateBudgetLiveData.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<Budget>, t: Throwable) {
                updateBudgetLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }


        })
    }
}