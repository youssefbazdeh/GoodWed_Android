package tn.esprit.wedding.viewmodels

import android.content.Context
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
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.ChecklistResponse
import tn.esprit.wedding.models.ResponseChecklist
import tn.esprit.wedding.models.loginResponse
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService

class ChecklistViewModel: ViewModel() {
    var checklistLiveData: MutableLiveData<MutableList<Checklist>?> = MutableLiveData()
    val _checklistLiveData : LiveData<MutableList<Checklist>?> = checklistLiveData

    var checklistLiveData1: MutableLiveData<ResponseChecklist?> = MutableLiveData()
    val _checklistLiveData1 : LiveData<ResponseChecklist?> = checklistLiveData1





    fun getCheckListById(id : String){
        val retrofit= ApiClient.getApiClient()!!.create(ChecklistService::class.java)
        val getChecklist=retrofit.getChecklistById(id)
        getChecklist.enqueue(object : Callback<ResponseChecklist> {
            override fun onResponse(call: Call<ResponseChecklist>, response: Response<ResponseChecklist>) {
                if (response.isSuccessful){
                    checklistLiveData1.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    checklistLiveData1.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<ResponseChecklist>, t: Throwable) {
                checklistLiveData1.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })

    }

    fun getAllChecklistByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(ChecklistService::class.java)
        val getChecklist=retrofit.getAllChecklistByIdUser(user_id)
        getChecklist.enqueue(object : Callback<MutableList<Checklist>> {
            override fun onResponse(call: Call<MutableList<Checklist>>, response: Response<MutableList<Checklist>>) {
                if (response.isSuccessful){
                    checklistLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    checklistLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<Checklist>>, t: Throwable) {
                checklistLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
}