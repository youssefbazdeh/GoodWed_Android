package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.GuestService

class AddGuestViewModel : ViewModel() {
    var addGuestLiveData : MutableLiveData<Guest?> = MutableLiveData()
    var _addGuestLiveData : LiveData<Guest?> = addGuestLiveData

    fun addGuest(guest: Guest ){
        val retrofit = ApiClient.getApiClient()!!.create(GuestService::class.java)
        val addguest = retrofit.addGuest(guest)
        addguest.enqueue(object : Callback<Guest>{
            override fun onResponse(call: Call<Guest>, response: Response<Guest>) {
                if (response.isSuccessful){
                    addGuestLiveData.postValue(response.body())
                }else{
                    Log.i("error body",response.errorBody()!!.toString())
                    addGuestLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Guest>, t: Throwable) {
                addGuestLiveData.postValue(null)
                Log.i("failure",t.message.toString())
            }

        })

    }
}