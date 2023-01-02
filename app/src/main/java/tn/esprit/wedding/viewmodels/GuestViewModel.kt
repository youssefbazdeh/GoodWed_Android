package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService
import tn.esprit.wedding.services.ChecklistService
import tn.esprit.wedding.services.GuestService

class GuestViewModel : ViewModel() {
    /***************** AFFICHAGE BY ID USER *****************/
    var guestMutableLiveData : MutableLiveData<MutableList<Guest>?> = MutableLiveData()
    var _guestMutableLiveData : LiveData<MutableList<Guest>?> = guestMutableLiveData

    fun getAllGuestByIdUser(user_id: String){
        val retrofit= ApiClient.getApiClient()!!.create(GuestService::class.java)
        val getBudget=retrofit.getAllGuestByIdUser(user_id)
        getBudget.enqueue(object : Callback<MutableList<Guest>> {
            override fun onResponse(call: Call<MutableList<Guest>>, response: Response<MutableList<Guest>>) {
                if (response.isSuccessful){
                    guestMutableLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    guestMutableLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<Guest>>, t: Throwable) {
                guestMutableLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
    /***************** AFFICHAGE BY ID GUEST *****************/
    var guestLiveData: MutableLiveData<Guest?> = MutableLiveData()
    val _guestLiveData : LiveData<Guest?> = guestLiveData

    fun getGuesttById(id : String){
        val retrofit= ApiClient.getApiClient()!!.create(GuestService::class.java)
        val getBudget=retrofit.getGuestById(id)
        getBudget.enqueue(object : Callback<Guest> {
            override fun onResponse(call: Call<Guest>, response: Response<Guest>) {
                if (response.isSuccessful){
                    guestLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    guestLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<Guest>, t: Throwable) {
                guestLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })

    }

    /***************** AFFICHAGE BY NOTE GUEST *****************/

    var guestnoteLiveData: MutableLiveData<MutableList<Guest>?> = MutableLiveData()
    val _guestnoteLiveData : LiveData<MutableList<Guest>?> = guestnoteLiveData


    fun getAllGuestByNote(user_id: String,note: String){
        val retrofit= ApiClient.getApiClient()!!.create(GuestService::class.java)
        val getGuest=retrofit.getAllGuestByNote(user_id,note)
        getGuest.enqueue(object : Callback<MutableList<Guest>> {
            override fun onResponse(call: Call<MutableList<Guest>>, response: Response<MutableList<Guest>>) {
                if (response.isSuccessful){
                    guestnoteLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    guestnoteLiveData.postValue(response.body())
                }

            }

            override fun onFailure(call: Call<MutableList<Guest>>, t: Throwable) {
                guestnoteLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }

        })
    }
}