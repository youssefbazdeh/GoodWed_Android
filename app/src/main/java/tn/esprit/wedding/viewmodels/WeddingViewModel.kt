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
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService
import tn.esprit.wedding.services.WeddingService
import java.util.Date

class WeddingViewModel : ViewModel()  {
    var addWeddingLiveData: MutableLiveData<Wedding?> = MutableLiveData()
    val _addWeddingLiveData : LiveData<Wedding?> = addWeddingLiveData

    var weddingLiveData: MutableLiveData<MutableList<Wedding>?> = MutableLiveData()
    val _weddingLiveData : LiveData<MutableList<Wedding>?> = weddingLiveData



    fun getWeddingByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(WeddingService::class.java)
        val getwed =retrofit.getWeddingByIdUser(user_id)
        getwed.enqueue(object : Callback<MutableList<Wedding>?> {
            override fun onResponse(call: Call<MutableList<Wedding>?>, response: Response<MutableList<Wedding>?>) {
                if (response.isSuccessful){
                    weddingLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    weddingLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<Wedding>?>, t: Throwable) {
                weddingLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }



    fun addWed(fullname: RequestBody,
               partner_fullname: RequestBody,
               genre: RequestBody,
               partner_sexe: RequestBody,
               partner_email: RequestBody,
               wedding_name: RequestBody,
               date_ceremonie: Date?,
               heure_ceremonie: Date?,
               budget: Int,
               user_id: RequestBody,
               image: MultipartBody.Part){
        val retrofit= ApiClient.getApiClient()!!.create(WeddingService::class.java)
        val addWedding=retrofit.addWed(fullname, partner_fullname, genre, partner_sexe, partner_email, wedding_name, date_ceremonie, heure_ceremonie, budget, user_id , image)
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