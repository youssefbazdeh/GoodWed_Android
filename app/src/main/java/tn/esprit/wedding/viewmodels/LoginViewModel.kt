package tn.esprit.wedding.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.models.loginResponse
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.UserService

class LoginViewModel : ViewModel() {
    var loginLiveData : MutableLiveData<loginResponse?> = MutableLiveData()
    var _logingLiveData : LiveData<loginResponse?> = loginLiveData


    fun getLoginObserver(): MutableLiveData<loginResponse?> {
        return  loginLiveData
    }
    fun login(email:String,password:String){

        val retrofit= ApiClient.getApiClient()!!.create(UserService::class.java)
        val addUser=retrofit.login(email,password)
        addUser.enqueue(object : Callback<loginResponse> {



            override fun onResponse(
                call: Call<loginResponse>,
                response: Response<loginResponse>
            ) {
                if (response.isSuccessful){
                    loginLiveData.postValue(response.body())
                }else{
                    Log.i("test",  response.errorBody()!!.string())

                    loginLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                loginLiveData.postValue(null)
                Log.i("failure", t.message.toString())                        }
        })
    }
}