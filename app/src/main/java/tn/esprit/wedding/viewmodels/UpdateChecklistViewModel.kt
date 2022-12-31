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
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService
import java.util.Date

class UpdateChecklistViewModel : ViewModel() {
    var updateChecklistLiveData : MutableLiveData<String?> = MutableLiveData()
    var _updateChecklistLiveData : LiveData<String?> = updateChecklistLiveData


    fun updateChecklist(
        _id:String,
        nom : RequestBody, type : RequestBody, note : RequestBody , date: Date, image : MultipartBody.Part?, status : RequestBody
    ){
        val retrofit = ApiClient.getApiClient()!!.create(ChecklistService::class.java)
        val addTask = retrofit.updateChecklist(_id, nom, type, note, image, date, status)
        addTask.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>){
                    if (response.isSuccessful){
                        updateChecklistLiveData.postValue(response.body())
                    }else{
                        Log.i("errorBody",  response.errorBody()!!.string())

                        updateChecklistLiveData.postValue(response.body())
                    }
                }
            override fun onFailure(call: Call<String>, t: Throwable) {
                updateChecklistLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }


        })
    }
}