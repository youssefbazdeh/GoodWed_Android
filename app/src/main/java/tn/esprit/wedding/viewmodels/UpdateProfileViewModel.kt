package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.ResponseUser
import tn.esprit.wedding.models.User
import tn.esprit.wedding.models.loginResponse
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.UserService
import java.util.Date

class UpdateProfileViewModel : ViewModel() {
    var updateProfileLiveData : MutableLiveData<User?> = MutableLiveData()
    var _updateProfileLiveData : LiveData<User?> = updateProfileLiveData



    var UserLiveData : MutableLiveData<ResponseUser?> = MutableLiveData()
    var _UserLiveData : LiveData<ResponseUser?> = UserLiveData


    fun getUserById(id:String){
        val retrofit = ApiClient.getApiClient()!!.create(UserService::class.java)
        val get = retrofit.getUserById(id)
        get.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>){
                if (response.isSuccessful){
                    UserLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    UserLiveData.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                UserLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }


        })

    }


    fun updateProfile(user_id : String , user: User){
        val retrofit = ApiClient.getApiClient()!!.create(UserService::class.java)
        val update = retrofit.update(user_id , user )
        update.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>){
                if (response.isSuccessful){
                    updateProfileLiveData.postValue(response.body())
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                    updateProfileLiveData.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                updateProfileLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
            }


        })

    }
}