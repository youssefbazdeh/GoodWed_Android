package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService

class ChecklistViewModel: ViewModel() {
    var checklistLiveData: MutableLiveData<MutableList<Checklist>?> = MutableLiveData()
    val _checklistLiveData : LiveData<MutableList<Checklist>?> = checklistLiveData

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