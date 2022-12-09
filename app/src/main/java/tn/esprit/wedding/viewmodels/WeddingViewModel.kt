package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.WeddingService

class WeddingViewModel : ViewModel()  {
    var addWeddingLiveData: MutableLiveData<Wedding> = MutableLiveData()
    val _addWeddingLiveData : LiveData<Wedding> = addWeddingLiveData

    fun addWed(wedding: Wedding){
        val retrofit= ApiClient.getApiClient()!!.create(WeddingService::class.java)
        val addWedding=retrofit.addWed(wedding)
        addWedding.enqueue(object : Callback<Wedding> {
            override fun onResponse(call: Call<Wedding>, response: Response<Wedding>) {
                if (response.isSuccessful){
                    addWeddingLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    addWeddingLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<Wedding>, t: Throwable) {
                addWeddingLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
}