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
import retrofit2.create
import retrofit2.http.Part
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService
import java.util.*

class TaskViewModel : ViewModel(){
    var addTaskLiveData : MutableLiveData<Checklist?> = MutableLiveData()
    var _addTaskLiveData : LiveData<Checklist?> = addTaskLiveData

    fun addChecklist(
        nom: RequestBody,
        type: RequestBody,
         note: RequestBody,
         image: MultipartBody.Part,
         date: Date,
         status: RequestBody,
         user_id: RequestBody){
        val retrofit = ApiClient.getApiClient()!!.create(ChecklistService::class.java)
        val addTask = retrofit.addChecklist(nom,type,note,image,date,status,user_id)
        addTask.enqueue(object : Callback<Checklist>{
            override fun onResponse(call: Call<Checklist>, response: Response<Checklist>) {
                if (response.isSuccessful){
                    addTaskLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody", response.errorBody()!!.string())
                    addTaskLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Checklist>, t: Throwable) {
                addTaskLiveData.postValue(null)
                Log.i("failure", t.message.toString())
            }
        })
    }
}