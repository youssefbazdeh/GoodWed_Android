package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService



class AddBudgetViewModel : ViewModel() {
    var addBudgetLiveData : MutableLiveData<Budget?> = MutableLiveData()
    var _addBudgetLiveData : LiveData<Budget?> = addBudgetLiveData

    fun addBudget(budget: Budget){
        val retrofit = ApiClient.getApiClient()!!.create(BudgetService::class.java)
        val addTask = retrofit.addBudget(budget)
        addTask.enqueue(object : Callback<Budget>{
            override fun onResponse(call: Call<Budget>, response: Response<Budget>) {
                if (response.isSuccessful){
                    addBudgetLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody", response.errorBody()!!.string())
                    addBudgetLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Budget>, t: Throwable) {
                addBudgetLiveData.postValue(null)
                Log.i("failure", t.message.toString())
            }
        })
    }
}