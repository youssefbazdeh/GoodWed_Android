package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService
import tn.esprit.wedding.services.GuestService

class UpdateGuestViewModel : ViewModel() {
    var updateGusetLiveData : MutableLiveData<Guest?> = MutableLiveData()
    var _updateGusetLiveData : LiveData<Guest?> = updateGusetLiveData


    fun updateGuest(
        _id:String,
        guest: Guest
    ){
        val retrofit = ApiClient.getApiClient()!!.create(GuestService::class.java)
        val update = retrofit.updateGuest(_id, guest)
        update.enqueue(object : Callback<Guest> {
            override fun onResponse(call: Call<Guest>, response: Response<Guest>){
                if (response.isSuccessful){
                    updateGusetLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    updateGusetLiveData.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<Guest>, t: Throwable) {
                updateGusetLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }


        })
    }
}